package com.syedhisham41.cron_validator.DTO;

import java.time.LocalDateTime;

import com.syedhisham41.cron_validator.Constants.StatusTypes;

public class CronEventStatusResponse {

    private String jobId;

    private String requestId;

    private String workerId;

    private StatusTypes statusType;

    private LocalDateTime workerUpdatedAt;

    public CronEventStatusResponse() {
    }

    public CronEventStatusResponse(String jobId, String requestId, StatusTypes statusType, LocalDateTime updatedAt,
            String workerId) {
        this.jobId = jobId;
        this.requestId = requestId;
        this.statusType = statusType;
        this.workerUpdatedAt = updatedAt;
        this.workerId = workerId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
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

    public LocalDateTime getWorkerUpdatedAt() {
        return workerUpdatedAt;
    }

    public void setWorkerUpdatedAt(LocalDateTime updatedAt) {
        this.workerUpdatedAt = updatedAt;
    }

}
