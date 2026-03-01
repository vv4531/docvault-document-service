package com.docvault.dto;

import java.time.OffsetDateTime;

public class DownloadResponseDto {
    private String status;
    private String sasUrl;
    private int expiryMinutes;
    private OffsetDateTime expiresAt;
    private String message;

    public static DownloadResponseDto ready(String sasUrl, int expiry, OffsetDateTime expiresAt) {
        DownloadResponseDto d = new DownloadResponseDto();
        d.status = "ready"; d.sasUrl = sasUrl;
        d.expiryMinutes = expiry; d.expiresAt = expiresAt;
        return d;
    }
    public static DownloadResponseDto rehydrating(String message) {
        DownloadResponseDto d = new DownloadResponseDto();
        d.status = "rehydrating"; d.message = message;
        return d;
    }
    public String getStatus()               { return status; }
    public String getSasUrl()               { return sasUrl; }
    public int getExpiryMinutes()           { return expiryMinutes; }
    public OffsetDateTime getExpiresAt()    { return expiresAt; }
    public String getMessage()              { return message; }
}