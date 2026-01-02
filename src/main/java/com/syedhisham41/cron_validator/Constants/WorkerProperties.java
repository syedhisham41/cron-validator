package com.syedhisham41.cron_validator.Constants;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "worker")
public class WorkerProperties {

    private String instanceId;
    private String type;
    private String version;
    private int maxConcurrency;
    private String hostname;
    private int heartBeatIntervalSecs;
    private String mqRequestExchange;
    private String mqRequestRouteKey;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMaxConcurrency() {
        return maxConcurrency;
    }

    public void setMaxConcurrency(int maxConcurrency) {
        this.maxConcurrency = maxConcurrency;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getMqRequestExchange() {
        return mqRequestExchange;
    }

    public void setMqRequestExchange(String mqRequestTopic) {
        this.mqRequestExchange = mqRequestTopic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeartBeatIntervalSecs() {
        return heartBeatIntervalSecs;
    }

    public void setHeartBeatIntervalSecs(int heartBeatIntervalSecs) {
        this.heartBeatIntervalSecs = heartBeatIntervalSecs;
    }

    public String getMqRequestRouteKey() {
        return mqRequestRouteKey;
    }

    public void setMqRequestRouteKey(String mqRequestRouteKey) {
        this.mqRequestRouteKey = mqRequestRouteKey;
    }

}