package com.docvault.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

// ── Download Response ─────────────────────────────────────────────────────
class DownloadResponseDto {
    private String         status;      // "ready" | "rehydrating"
    private String         sasUrl;
    private int            expiryMinutes;
    private OffsetDateTime expiresAt;
    private String         message;

    public static DownloadResponseDto ready(String sasUrl, int expiry, OffsetDateTime expiresAt) {
        DownloadResponseDto d = new DownloadResponseDto();
        d.status = "ready"; d.sasUrl = sasUrl; d.expiryMinutes = expiry; d.expiresAt = expiresAt;
        return d;
    }
    public static DownloadResponseDto rehydrating(String message) {
        DownloadResponseDto d = new DownloadResponseDto();
        d.status = "rehydrating"; d.message = message;
        return d;
    }
    public String         getStatus()        { return status; }
    public String         getSasUrl()         { return sasUrl; }
    public int            getExpiryMinutes()  { return expiryMinutes; }
    public OffsetDateTime getExpiresAt()      { return expiresAt; }
    public String         getMessage()        { return message; }
}

// ── Rehydration Request/Response ──────────────────────────────────────────
class RehydrationRequestDto {
    private String priority = "Standard";
    public String getPriority()        { return priority; }
    public void   setPriority(String v){ this.priority = v; }
}

class RehydrationResponseDto {
    private String         status;
    private String         priority;
    private String         message;
    private OffsetDateTime estimatedReadyAt;

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final RehydrationResponseDto dto = new RehydrationResponseDto();
        public Builder status(String v)             { dto.status = v; return this; }
        public Builder priority(String v)           { dto.priority = v; return this; }
        public Builder message(String v)            { dto.message = v; return this; }
        public Builder estimatedReadyAt(OffsetDateTime v) { dto.estimatedReadyAt = v; return this; }
        public RehydrationResponseDto build()       { return dto; }
    }
    public String         getStatus()          { return status; }
    public String         getPriority()        { return priority; }
    public String         getMessage()         { return message; }
    public OffsetDateTime getEstimatedReadyAt(){ return estimatedReadyAt; }
}

// ── Update Document ───────────────────────────────────────────────────────
class UpdateDocumentDto {
    private String       title;
    private String       author;
    private String       department;
    private List<String> tags;
    private String       description;

    public String       getTitle()              { return title; }
    public void         setTitle(String v)      { this.title = v; }
    public String       getAuthor()             { return author; }
    public void         setAuthor(String v)     { this.author = v; }
    public String       getDepartment()         { return department; }
    public void         setDepartment(String v) { this.department = v; }
    public List<String> getTags()               { return tags; }
    public void         setTags(List<String> v) { this.tags = v; }
    public String       getDescription()        { return description; }
    public void         setDescription(String v){ this.description = v; }
}

// ── Stats ─────────────────────────────────────────────────────────────────
class StatsDto {
    private long            total;
    private long            hot;
    private long            cool;
    private long            cold;
    private long            archive;
    private long            totalMb;
    private List<Map<String,Object>> byDepartment;

    public long            getTotal()              { return total; }
    public void            setTotal(long v)        { this.total = v; }
    public long            getHot()                { return hot; }
    public void            setHot(long v)          { this.hot = v; }
    public long            getCool()               { return cool; }
    public void            setCool(long v)         { this.cool = v; }
    public long            getCold()               { return cold; }
    public void            setCold(long v)         { this.cold = v; }
    public long            getArchive()            { return archive; }
    public void            setArchive(long v)      { this.archive = v; }
    public long            getTotalMb()            { return totalMb; }
    public void            setTotalMb(long v)      { this.totalMb = v; }
    public List<Map<String,Object>> getByDepartment() { return byDepartment; }
    public void setByDepartment(List<Map<String,Object>> v) { this.byDepartment = v; }
}

// ── Blob Upload Result (internal) ─────────────────────────────────────────
class BlobUploadResult {
    private String blobName;
    private String blobUrl;
    private String containerName;
    private String etag;
    private String storageTier;

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final BlobUploadResult r = new BlobUploadResult();
        public Builder blobName(String v)     { r.blobName = v;     return this; }
        public Builder blobUrl(String v)      { r.blobUrl = v;      return this; }
        public Builder containerName(String v){ r.containerName = v; return this; }
        public Builder etag(String v)         { r.etag = v;         return this; }
        public Builder storageTier(String v)  { r.storageTier = v;  return this; }
        public BlobUploadResult build()       { return r; }
    }
    public String getBlobName()      { return blobName; }
    public String getBlobUrl()       { return blobUrl; }
    public String getContainerName() { return containerName; }
    public String getEtag()          { return etag; }
    public String getStorageTier()   { return storageTier; }
}

// ── SAS Result (internal) ─────────────────────────────────────────────────
class SasResult {
    private String         status;
    private String         sasUrl;
    private int            expiryMinutes;
    private OffsetDateTime expiresAt;
    private String         message;

    public static SasResult ready(String sasUrl, int expiry, OffsetDateTime expiresAt) {
        SasResult s = new SasResult(); s.status = "ready"; s.sasUrl = sasUrl;
        s.expiryMinutes = expiry; s.expiresAt = expiresAt; return s;
    }
    public static SasResult rehydrating(String message) {
        SasResult s = new SasResult(); s.status = "rehydrating"; s.message = message; return s;
    }
    public String         getStatus()       { return status; }
    public String         getSasUrl()       { return sasUrl; }
    public int            getExpiryMinutes(){ return expiryMinutes; }
    public OffsetDateTime getExpiresAt()    { return expiresAt; }
    public String         getMessage()      { return message; }
}

// ── Rehydration Result (internal) ─────────────────────────────────────────
class RehydrationResult {
    private String         status;
    private String         priority;
    private String         message;
    private OffsetDateTime estimatedReadyAt;

    public static RehydrationResult started(String priority, OffsetDateTime eta) {
        RehydrationResult r = new RehydrationResult();
        r.status = "rehydrating"; r.priority = priority; r.estimatedReadyAt = eta;
        r.message = "Rehydration started with " + priority + " priority."; return r;
    }
    public static RehydrationResult alreadyPending(String priority) {
        RehydrationResult r = new RehydrationResult();
        r.status = "rehydrate-pending"; r.priority = priority;
        r.message = "Rehydration already in progress."; return r;
    }
    public String         getStatus()          { return status; }
    public String         getPriority()        { return priority; }
    public String         getMessage()         { return message; }
    public OffsetDateTime getEstimatedReadyAt(){ return estimatedReadyAt; }
}
