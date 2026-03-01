package com.docvault.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Document metadata stored in Azure Cosmos DB.
 * Blob binary is stored separately in Azure Blob Storage.
 * Partition key = department for efficient per-department queries.
 */
@Container(containerName = "documents")
public class Document {

    @Id
    private String id;

    private String title;
    private String author;

    @PartitionKey
    private String department;

    private List<String> tags;
    private String description;

    // File metadata
    private String filename;
    private String mimeType;
    private long   fileSizeBytes;

    // Blob reference
    private String blobName;
    private String blobUrl;
    private String containerName;
    private String etag;

    // Lifecycle
    private String         storageTier;
    private OffsetDateTime uploadedAt;
    private OffsetDateTime lastAccessedAt;
    private OffsetDateTime archivedAt;
    private String         restoreStatus;
    private OffsetDateTime restoreRequestedAt;

    // ── Getters & Setters ─────────────────────────────────────────────────
    public String getId()                        { return id; }
    public void   setId(String id)               { this.id = id; }
    public String getTitle()                     { return title; }
    public void   setTitle(String title)         { this.title = title; }
    public String getAuthor()                    { return author; }
    public void   setAuthor(String author)       { this.author = author; }
    public String getDepartment()                { return department; }
    public void   setDepartment(String d)        { this.department = d; }
    public List<String> getTags()                { return tags; }
    public void   setTags(List<String> tags)     { this.tags = tags; }
    public String getDescription()               { return description; }
    public void   setDescription(String d)       { this.description = d; }
    public String getFilename()                  { return filename; }
    public void   setFilename(String f)          { this.filename = f; }
    public String getMimeType()                  { return mimeType; }
    public void   setMimeType(String m)          { this.mimeType = m; }
    public long   getFileSizeBytes()             { return fileSizeBytes; }
    public void   setFileSizeBytes(long s)       { this.fileSizeBytes = s; }
    public String getBlobName()                  { return blobName; }
    public void   setBlobName(String b)          { this.blobName = b; }
    public String getBlobUrl()                   { return blobUrl; }
    public void   setBlobUrl(String b)           { this.blobUrl = b; }
    public String getContainerName()             { return containerName; }
    public void   setContainerName(String c)     { this.containerName = c; }
    public String getEtag()                      { return etag; }
    public void   setEtag(String e)              { this.etag = e; }
    public String getStorageTier()               { return storageTier; }
    public void   setStorageTier(String s)       { this.storageTier = s; }
    public OffsetDateTime getUploadedAt()        { return uploadedAt; }
    public void   setUploadedAt(OffsetDateTime t){ this.uploadedAt = t; }
    public OffsetDateTime getLastAccessedAt()    { return lastAccessedAt; }
    public void   setLastAccessedAt(OffsetDateTime t) { this.lastAccessedAt = t; }
    public OffsetDateTime getArchivedAt()        { return archivedAt; }
    public void   setArchivedAt(OffsetDateTime t){ this.archivedAt = t; }
    public String getRestoreStatus()             { return restoreStatus; }
    public void   setRestoreStatus(String s)     { this.restoreStatus = s; }
    public OffsetDateTime getRestoreRequestedAt(){ return restoreRequestedAt; }
    public void   setRestoreRequestedAt(OffsetDateTime t) { this.restoreRequestedAt = t; }
}
