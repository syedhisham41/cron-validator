package com.syedhisham41.cron_validator.DTO;

import java.time.Instant;

public class CronEventResponse {

    private String jobId;

    private String requestId;

    private boolean valid;

    private String summary;

    private String errorMessage;

    private Instant validatedAt;

    public CronEventResponse() {
    }

    public CronEventResponse(String jobId, String requestId, boolean valid, String summary, String errorMessage,
            Instant validatedAt) {
        this.jobId = jobId;
        this.requestId = requestId;
        this.valid = valid;
        this.summary = summary;
        this.errorMessage = errorMessage;
        this.validatedAt = validatedAt;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(Instant validatedAt) {
        this.validatedAt = validatedAt;
    }

}
