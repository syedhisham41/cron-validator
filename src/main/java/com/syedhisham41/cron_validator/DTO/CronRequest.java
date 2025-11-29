package com.syedhisham41.cron_validator.DTO;

import com.syedhisham41.cron_validator.Constants.CronType;

public class CronRequest {

    private String cronExpr;

    private CronType cronType;

    public CronRequest() {
    }

    public CronRequest(String cronExpr, CronType cronType) {
        this.cronExpr = cronExpr;
        this.cronType = cronType;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public CronType getCronType() {
        return cronType;
    }

    public void setCronType(CronType cronType) {
        this.cronType = cronType;
    }

}
