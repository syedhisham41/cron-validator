package com.syedhisham41.cron_validator.DTO;

import java.time.LocalDateTime;

import com.syedhisham41.cron_validator.Constants.CronType;

public class CronEventRequest {

    private String jobId;

    private String cronValue;

    private CronType cronType;

    private String requestId;

    private LocalDateTime createdAt;

    public CronEventRequest() {
    }

    public CronEventRequest(String jobId, String cronValue, CronType cronType, String requestId, LocalDateTime createdAt) {
        this.jobId = jobId;
        this.cronValue = cronValue;
        this.cronType = cronType;
        this.requestId = requestId;
        this.createdAt = createdAt;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCronValue() {
        return cronValue;
    }

    public void setCronValue(String cronValue) {
        this.cronValue = cronValue;
    }

    public CronType getCronType() {
        return cronType;
    }

    public void setCronType(CronType cronType) {
        this.cronType = cronType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}