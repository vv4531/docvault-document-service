package com.docvault.dto;

import java.time.OffsetDateTime;

public class RehydrationResult {
    private String         status;
    private String         priority;
    private String         message;
    private OffsetDateTime estimatedReadyAt;

    public String         getStatus()           { return status; }
    public void           setStatus(String v)                   { this.status = v; }
    public String         getPriority()         { return priority; }
    public void           setPriority(String v)                 { this.priority = v; }
    public String         getMessage()          { return message; }
    public void           setMessage(String v)                  { this.message = v; }
    public OffsetDateTime getEstimatedReadyAt() { return estimatedReadyAt; }
    public void           setEstimatedReadyAt(OffsetDateTime v) { this.estimatedReadyAt = v; }

    public static RehydrationResult started(String priority, OffsetDateTime estimatedReadyAt) {
        RehydrationResult r = new RehydrationResult();
        r.status = "rehydrating";
        r.priority = priority;
        r.message = "Rehydration initiated with " + priority + " priority.";
        r.estimatedReadyAt = estimatedReadyAt;
        return r;
    }

    public static RehydrationResult alreadyPending(String priority) {
        RehydrationResult r = new RehydrationResult();
        r.status = "rehydrate-pending";
        r.priority = priority;
        r.message = "Rehydration already in progress.";
        return r;
    }
}
