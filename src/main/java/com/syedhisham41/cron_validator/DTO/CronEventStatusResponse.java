package com.syedhisham41.cron_validator.DTO;

import java.time.Instant;

import com.syedhisham41.cron_validator.Constants.StatusTypes;

public class CronEventStatusResponse {

    private String jobId;

    private String requestId;

    private StatusTypes statusType;

    private Instant updatedAt;

    public CronEventStatusResponse() {
    }

    public CronEventStatusResponse(String jobId, String requestId, StatusTypes statusType, Instant updatedAt) {
        this.jobId = jobId;
        this.requestId = requestId;
        this.statusType = statusType;
        this.updatedAt = updatedAt;
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

    public StatusTypes getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusTypes statusType) {
        this.statusType = statusType;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
