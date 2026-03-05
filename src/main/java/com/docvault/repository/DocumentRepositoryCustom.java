package com.docvault.repository;

import com.docvault.dto.StatsDto;
import com.docvault.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface DocumentRepositoryCustom {
    Page<Document> findAllFiltered(String tier, String department, Pageable pageable);
    Page<Document> searchMetadata(String q, Pageable pageable);
    List<Document> findHotDocsOlderThan(OffsetDateTime cutoff);
    StatsDto       getStats();
    void           updateLastAccessed(String id, OffsetDateTime at);
    void           patchRestoreStatus(String id, String status, OffsetDateTime at);
}
