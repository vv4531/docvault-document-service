package com.docvault.dto;

public class BlobUploadResult {
    private String blobName;
    private String blobUrl;
    private String containerName;
    private String etag;
    private String storageTier;

    public String getBlobName()      { return blobName; }
    public void   setBlobName(String v)      { this.blobName = v; }
    public String getBlobUrl()       { return blobUrl; }
    public void   setBlobUrl(String v)       { this.blobUrl = v; }
    public String getContainerName() { return containerName; }
    public void   setContainerName(String v) { this.containerName = v; }
    public String getEtag()          { return etag; }
    public void   setEtag(String v)          { this.etag = v; }
    public String getStorageTier()   { return storageTier; }
    public void   setStorageTier(String v)   { this.storageTier = v; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final BlobUploadResult r = new BlobUploadResult();

        public Builder blobName(String v)      { r.blobName = v;      return this; }
        public Builder blobUrl(String v)       { r.blobUrl = v;       return this; }
        public Builder containerName(String v) { r.containerName = v; return this; }
        public Builder etag(String v)          { r.etag = v;          return this; }
        public Builder storageTier(String v)   { r.storageTier = v;   return this; }

        public BlobUploadResult build()        { return r; }
    }
}
