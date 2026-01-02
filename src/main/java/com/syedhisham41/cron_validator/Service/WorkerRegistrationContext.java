package com.syedhisham41.cron_validator.Service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.syedhisham41.cron_validator.DTO.OrchestratorRegisterResponse;

@Component
public class WorkerRegistrationContext {

    private UUID workerId;
    private String workerName;

    private String statusExchange;
    private String statusRoutingKey;

    private String resultExchange;
    private String resultRoutingKey;

    private volatile boolean registered = false;

    public void initialize(OrchestratorRegisterResponse response) {

        this.workerId = response.getWorkerId();
        this.workerName = response.getWorkerName();
        this.statusExchange = response.getJoStatusExchange();
        this.statusRoutingKey = response.getJoStatusRoutingKey();
        this.resultExchange = response.getJoResultExchange();
        this.resultRoutingKey = response.getJoResultRoutingKey();
        this.registered = true;

    }

    public void assertRegistered() {
        if (!registered) {
            throw new IllegalStateException("Worker not registered with Job Orchestrator");
        }
    }

    public UUID getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getStatusExchange() {
        return statusExchange;
    }

    public String getStatusRoutingKey() {
        return statusRoutingKey;
    }

    public String getResultExchange() {
        return resultExchange;
    }

    public String getResultRoutingKey() {
        return resultRoutingKey;
    }

    public boolean isRegistered() {
        return registered;
    }

}
