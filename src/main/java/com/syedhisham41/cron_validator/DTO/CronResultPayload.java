package com.syedhisham41.cron_validator.DTO;

public class CronResultPayload {

    private String summary;

    private boolean valid;

    public CronResultPayload() {
    }

    public CronResultPayload(String summary, boolean valid) {
        this.summary = summary;
        this.valid = valid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
