package com.docvault.controller;

import com.docvault.dto.*;
import com.docvault.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * REST controller for document management operations.
 *
 * All endpoints are accessible through Azure APIM at:
 *   /documents/v1/**
 *
 * The Node.js BFF is the only caller — client services never
 * hit this controller directly.
 */
@RestController
@RequestMapping("/v1/documents")
@Tag(name = "Documents", description = "Document lifecycle management")
public class DocumentController {

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // ── GET /v1/documents — paginated list ────────────────────────────────
    @GetMapping
    @Operation(summary = "List documents with pagination and filters")
    public ResponseEntity<Page<DocumentDto>> listDocuments(
            @RequestParam(required = false) String tier,
            @RequestParam(required = false) String department,
            @PageableDefault(size = 25, sort = "uploadedAt") Pageable pageable) {
        return ResponseEntity.ok(documentService.list(tier, department, pageable));
    }

    // ── GET /v1/documents/stats ───────────────────────────────────────────
    @GetMapping("/stats")
    @Operation(summary = "Aggregated storage statistics")
    public ResponseEntity<StatsDto> getStats() {
        return ResponseEntity.ok(documentService.getStats());
    }

    // ── GET /v1/documents/:id ─────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Get document metadata by ID")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable String id) {
        return ResponseEntity.ok(documentService.getById(id));
    }

    // ── POST /v1/documents — upload ───────────────────────────────────────
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a document to Azure Blob Storage")
    public ResponseEntity<DocumentDto> uploadDocument(
            @RequestPart("file")     MultipartFile file,
            @RequestPart("metadata") @Valid UploadMetadataDto metadata) throws IOException {

        log.info("[Controller] Upload: {} ({} bytes)", file.getOriginalFilename(), file.getSize());
        DocumentDto created = documentService.upload(file, metadata);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── GET /v1/documents/:id/download — SAS URL or rehydration ──────────
    @GetMapping("/{id}/download")
    @Operation(summary = "Generate SAS download URL (or trigger rehydration for Archive blobs)")
    public ResponseEntity<DownloadResponseDto> downloadDocument(
            @PathVariable String id,
            @RequestParam(defaultValue = "Standard") String priority) {

        DownloadResponseDto response = documentService.getDownloadUrl(id, priority);
        int status = "rehydrating".equals(response.getStatus()) ? 202 : 200;
        return ResponseEntity.status(status).body(response);
    }

    // ── POST /v1/documents/:id/rehydrate — Archive rehydration ───────────
    @PostMapping("/{id}/rehydrate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Initiate Archive-tier rehydration")
    public ResponseEntity<RehydrationResponseDto> rehydrate(
            @PathVariable String id,
            @RequestBody(required = false) RehydrationRequestDto request) {

        String priority = request != null ? request.getPriority() : "Standard";
        return ResponseEntity.accepted().body(documentService.rehydrate(id, priority));
    }

    // ── PATCH /v1/documents/:id — update metadata ─────────────────────────
    @PatchMapping("/{id}")
    @Operation(summary = "Update document metadata")
    public ResponseEntity<DocumentDto> updateDocument(
            @PathVariable String id,
            @RequestBody @Valid UpdateDocumentDto updateDto) {
        return ResponseEntity.ok(documentService.update(id, updateDto));
    }

    // ── DELETE /v1/documents/:id ──────────────────────────────────────────
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a document and its blob")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── POST /v1/documents/reindex — bulk re-index all docs in AI Search ──
    @PostMapping("/reindex")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Re-index all existing documents in Azure AI Search")
    public ResponseEntity<Map<String, Object>> reindexAll() {
        int count = documentService.reindexAll();
        return ResponseEntity.accepted().body(Map.of("indexed", count));
    }
}
