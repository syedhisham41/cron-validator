package com.syedhisham41.cron_validator.DTO;

import java.util.UUID;

public class OrchestratorRegisterResponse {

    private UUID workerId;

    private String workerName;

    private String joStatusExchange;

    private String joStatusRoutingKey;

    private String joResultExchange;

    private String joResultRoutingKey;

    public OrchestratorRegisterResponse() {
    }

    public OrchestratorRegisterResponse(UUID workerId, String workerName, String joStatusExchange,
            String joStatusRoutingKey, String joResultExchange, String joResultRoutingKey) {
        this.workerId = workerId;
        this.workerName = workerName;
        this.joStatusExchange = joStatusExchange;
        this.joStatusRoutingKey = joStatusRoutingKey;
        this.joResultExchange = joResultExchange;
        this.joResultRoutingKey = joResultRoutingKey;
    }

    public UUID getWorkerId() {
        return workerId;
    }

    public void setWorkerId(UUID workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getJoStatusExchange() {
        return joStatusExchange;
    }

    public void setJoStatusExchange(String joStatusExchange) {
        this.joStatusExchange = joStatusExchange;
    }

    public String getJoStatusRoutingKey() {
        return joStatusRoutingKey;
    }

    public void setJoStatusRoutingKey(String joStatusRoutingKey) {
        this.joStatusRoutingKey = joStatusRoutingKey;
    }

    public String getJoResultExchange() {
        return joResultExchange;
    }

    public void setJoResultExchange(String joResultExchange) {
        this.joResultExchange = joResultExchange;
    }

    public String getJoResultRoutingKey() {
        return joResultRoutingKey;
    }

    public void setJoResultRoutingKey(String joResultRoutingKey) {
        this.joResultRoutingKey = joResultRoutingKey;
    }

}
