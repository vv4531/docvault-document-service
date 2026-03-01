package com.docvault.dto;

import java.time.OffsetDateTime;

public class SasResult {
    private String         status;
    private String         sasUrl;
    private int            expiryMinutes;
    private OffsetDateTime expiresAt;
    private String         message;

    public String         getStatus()        { return status; }
    public void           setStatus(String v)             { this.status = v; }
    public String         getSasUrl()        { return sasUrl; }
    public void           setSasUrl(String v)             { this.sasUrl = v; }
    public int            getExpiryMinutes() { return expiryMinutes; }
    public void           setExpiryMinutes(int v)         { this.expiryMinutes = v; }
    public OffsetDateTime getExpiresAt()     { return expiresAt; }
    public void           setExpiresAt(OffsetDateTime v)  { this.expiresAt = v; }
    public String         getMessage()       { return message; }
    public void           setMessage(String v)            { this.message = v; }

    public static SasResult ready(String sasUrl, int expiryMinutes, OffsetDateTime expiresAt) {
        SasResult r = new SasResult();
        r.status = "ready";
        r.sasUrl = sasUrl;
        r.expiryMinutes = expiryMinutes;
        r.expiresAt = expiresAt;
        return r;
    }

    public static SasResult rehydrating(String message) {
        SasResult r = new SasResult();
        r.status = "rehydrating";
        r.message = message;
        return r;
    }
}
