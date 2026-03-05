package com.docvault.service;

import com.docvault.dto.BlobUploadResult;
import com.docvault.model.Document;
import com.docvault.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Scheduled service that moves Hot-tier documents to the archive (Cool)
 * storage account after a configurable delay.
 *
 * Flow: docvaultstor/documents-hot → docvaultarchive/documents-cool
 * Cosmos DB and AI Search are updated to reflect the new location and tier.
 *
 * Configuration (application.yml / env vars):
 *   tier-migration.hot-to-cool-after-hours  (TIER_MIGRATION_HOT_TO_COOL_HOURS)  default: 1
 *   tier-migration.schedule-interval-ms     (TIER_MIGRATION_INTERVAL_MS)         default: 300000 (5 min)
 */
@Service
public class TierMigrationService {

    private static final Logger log = LoggerFactory.getLogger(TierMigrationService.class);

    @Value("${search.service.url:}")
    private String searchServiceUrl;

    @Value("${tier-migration.hot-to-cool-after-hours:1}")
    private int hotToCoolAfterHours;

    private final BlobStorageService blobService;
    private final DocumentRepository repository;
    private final RestTemplate       restTemplate = new RestTemplate();

    public TierMigrationService(BlobStorageService blobService, DocumentRepository repository) {
        this.blobService = blobService;
        this.repository  = repository;
    }

    /**
     * Runs on a configurable interval (default 5 min).
     * Finds all Hot-tier documents uploaded more than hotToCoolAfterHours ago
     * and moves them to Cool (archive) storage.
     */
    @Scheduled(fixedDelayString = "${tier-migration.schedule-interval-ms:300000}")
    public void migrateHotToCool() {
        OffsetDateTime cutoff = OffsetDateTime.now().minusHours(hotToCoolAfterHours);
        List<Document> candidates = repository.findHotDocsOlderThan(cutoff);

        if (candidates.isEmpty()) {
            log.debug("[Migration] No Hot documents older than {} hour(s) found.", hotToCoolAfterHours);
            return;
        }

        log.info("[Migration] Found {} document(s) to migrate to Cool storage.", candidates.size());

        for (Document doc : candidates) {
            try {
                // 1. Copy blob to archive account, delete from hot
                BlobUploadResult result = blobService.copyToArchive(
                        doc.getContainerName(),
                        doc.getBlobName(),
                        doc.getMimeType() != null ? doc.getMimeType() : "application/octet-stream");

                // 2. Update Cosmos DB metadata
                doc.setContainerName(result.getContainerName());
                doc.setBlobUrl(result.getBlobUrl());
                doc.setStorageTier("Cool");
                doc.setArchivedAt(OffsetDateTime.now());
                repository.save(doc);

                // 3. Update AI Search index storageTier
                updateSearchTier(doc.getId(), "Cool");

                log.info("[Migration] Migrated docId={} → Cool ({})", doc.getId(), result.getBlobUrl());
            } catch (Exception e) {
                log.error("[Migration] Failed to migrate docId={}: {}", doc.getId(), e.getMessage());
            }
        }
    }

    private void updateSearchTier(String docId, String tier) {
        if (searchServiceUrl == null || searchServiceUrl.isBlank()) return;
        try {
            Map<String, Object> patch = new HashMap<>();
            patch.put("id",          docId);
            patch.put("storageTier", tier);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.postForEntity(searchServiceUrl + "/v1/search/index",
                    new HttpEntity<>(patch, headers), Void.class);
        } catch (Exception e) {
            log.warn("[Migration] Search tier update failed for docId={}: {}", docId, e.getMessage());
        }
    }
}
