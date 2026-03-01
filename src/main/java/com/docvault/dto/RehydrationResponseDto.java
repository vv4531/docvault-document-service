package com.docvault.dto;

import java.time.OffsetDateTime;

public class RehydrationResponseDto {
    private String         status;
    private String         priority;
    private String         message;
    private OffsetDateTime estimatedReadyAt;

    public String         getStatus()           { return status; }
    public String         getPriority()         { return priority; }
    public String         getMessage()          { return message; }
    public OffsetDateTime getEstimatedReadyAt() { return estimatedReadyAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final RehydrationResponseDto dto = new RehydrationResponseDto();

        public Builder status(String v)                   { dto.status = v;           return this; }
        public Builder priority(String v)                 { dto.priority = v;         return this; }
        public Builder message(String v)                  { dto.message = v;          return this; }
        public Builder estimatedReadyAt(OffsetDateTime v) { dto.estimatedReadyAt = v; return this; }

        public RehydrationResponseDto build()             { return dto; }
    }
}