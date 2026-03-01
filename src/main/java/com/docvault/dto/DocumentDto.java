package com.docvault.dto;

import java.time.OffsetDateTime;
import java.util.List;

public class DocumentDto {
    private String         id;
    private String         title;
    private String         author;
    private String         department;
    private List<String>   tags;
    private String         description;
    private String         filename;
    private String         mimeType;
    private long           fileSizeBytes;
    private String         blobName;
    private String         storageTier;
    private OffsetDateTime uploadedAt;
    private OffsetDateTime lastAccessedAt;
    private OffsetDateTime archivedAt;
    private String         restoreStatus;

    public String         getId()                         { return id; }
    public void           setId(String v)                 { this.id = v; }
    public String         getTitle()                      { return title; }
    public void           setTitle(String v)              { this.title = v; }
    public String         getAuthor()                     { return author; }
    public void           setAuthor(String v)             { this.author = v; }
    public String         getDepartment()                 { return department; }
    public void           setDepartment(String v)         { this.department = v; }
    public List<String>   getTags()                       { return tags; }
    public void           setTags(List<String> v)         { this.tags = v; }
    public String         getDescription()                { return description; }
    public void           setDescription(String v)        { this.description = v; }
    public String         getFilename()                   { return filename; }
    public void           setFilename(String v)           { this.filename = v; }
    public String         getMimeType()                   { return mimeType; }
    public void           setMimeType(String v)           { this.mimeType = v; }
    public long           getFileSizeBytes()              { return fileSizeBytes; }
    public void           setFileSizeBytes(long v)        { this.fileSizeBytes = v; }
    public String         getBlobName()                   { return blobName; }
    public void           setBlobName(String v)           { this.blobName = v; }
    public String         getStorageTier()                { return storageTier; }
    public void           setStorageTier(String v)        { this.storageTier = v; }
    public OffsetDateTime getUploadedAt()                 { return uploadedAt; }
    public void           setUploadedAt(OffsetDateTime v) { this.uploadedAt = v; }
    public OffsetDateTime getLastAccessedAt()             { return lastAccessedAt; }
    public void           setLastAccessedAt(OffsetDateTime v) { this.lastAccessedAt = v; }
    public OffsetDateTime getArchivedAt()                 { return archivedAt; }
    public void           setArchivedAt(OffsetDateTime v) { this.archivedAt = v; }
    public String         getRestoreStatus()              { return restoreStatus; }
    public void           setRestoreStatus(String v)      { this.restoreStatus = v; }
}
