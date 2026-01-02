package com.syedhisham41.cron_validator.DTO;

public class OrchestratorRegisterRequest {

    private String instanceId;

    private String workerType;

    private String workerVersion;

    private String hostname;

    private int maxConcurrency;

    private int heartBeatIntervalSecs;

    private String mqRequestExchange;

    private String mqRequestRouteKey;

    private String tags;

    private String workerSchemaJson;

    public OrchestratorRegisterRequest() {
    }

    public OrchestratorRegisterRequest(String instanceId, String workerType, String version, String hostname,
            int maxConcurrency, int heartBeatInterval, String mqRequestTopic, String tags, String mqRequestRouteKey,
            String workerSchemaJson) {
        this.instanceId = instanceId;
        this.workerType = workerType;
        this.workerVersion = version;
        this.hostname = hostname;
        this.maxConcurrency = maxConcurrency;
        this.heartBeatIntervalSecs = heartBeatInterval;
        this.mqRequestExchange = mqRequestTopic;
        this.tags = tags;
        this.mqRequestRouteKey = mqRequestRouteKey;
        this.workerSchemaJson = workerSchemaJson;
    }

    public String getMqRequestRouteKey() {
        return mqRequestRouteKey;
    }

    public void setMqRequestRouteKey(String mqRequestRouteKey) {
        this.mqRequestRouteKey = mqRequestRouteKey;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getWorkerType() {
        return workerType;
    }

    public String getWorkerSchemaJson() {
        return workerSchemaJson;
    }

    public void setWorkerSchemaJson(String workerSchemaJson) {
        this.workerSchemaJson = workerSchemaJson;
    }

    public int getHeartBeatIntervalSecs() {
        return heartBeatIntervalSecs;
    }

    public void setHeartBeatIntervalSecs(int heartBeatIntervalSecs) {
        this.heartBeatIntervalSecs = heartBeatIntervalSecs;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getWorkerVersion() {
        return workerVersion;
    }

    public void setWorkerVersion(String version) {
        this.workerVersion = version;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getMaxConcurrency() {
        return maxConcurrency;
    }

    public void setMaxConcurrency(int maxConcurrency) {
        this.maxConcurrency = maxConcurrency;
    }

    public String getMqRequestExchange() {
        return mqRequestExchange;
    }

    public void setMqRequestExchange(String mqRequestTopic) {
        this.mqRequestExchange = mqRequestTopic;
    }

}
