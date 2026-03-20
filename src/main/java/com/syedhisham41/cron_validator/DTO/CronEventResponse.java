package com.syedhisham41.cron_validator.DTO;

import java.time.LocalDateTime;
import java.util.Map;

import com.syedhisham41.cron_validator.Constants.ResultTypes;

public class CronEventResponse {

    private String jobId;

    private String requestId;

    private String workerId;

    private ResultTypes resultStatus;

    private Map<String, Object> payload;

    private String errorMessage;

    private LocalDateTime completedAt;

    public CronEventResponse() {
    }

    public CronEventResponse(String jobId, String requestId, String workerId, ResultTypes resultStatus,
            Map<String, Object> payload,
            String errorMessage, LocalDateTime completedAt) {
        this.jobId = jobId;
        this.requestId = requestId;
        this.workerId = workerId;
        this.payload = payload;
        this.errorMessage = errorMessage;
        this.completedAt = completedAt;
        this.resultStatus = resultStatus;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public ResultTypes getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultTypes resultStatus) {
        this.resultStatus = resultStatus;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

}
