package com.syedhisham41.cron_validator.Service;

import java.net.URI;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.syedhisham41.cron_validator.Constants.WorkerProperties;
import com.syedhisham41.cron_validator.DTO.OrchestratorRegisterRequest;
import com.syedhisham41.cron_validator.DTO.OrchestratorRegisterResponse;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class OrchestratorRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(OrchestratorRegisterService.class);

    @Value("${worker.job.orchestrator.url}")
    private String orchestratorUrl;

    private final ScheduledExecutorService executorService;

    private WorkerProperties properties;

    private RestClient client;

    private final WorkerSchemaLoader schemaLoader;

    private volatile boolean registered = false;

    private final WorkerRegistrationContext context;

    public OrchestratorRegisterService(ScheduledExecutorService executorService, WorkerProperties properties,
            RestClient.Builder restClientBuilder, WorkerSchemaLoader schemaLoader, WorkerRegistrationContext context) {
        this.executorService = executorService;
        this.properties = properties;
        this.client = restClientBuilder.build();
        this.schemaLoader = schemaLoader;
        this.context = context;
    }

    public void registerWorker() {
        logger.info("orchestrator registration initiated");
        try {

            logger.info(
                    "instanceId {} type {} version {} hostname {} concurrency {} heartbeatinterval {} topic {} key {}",
                    properties.getInstanceId(), properties.getType(), properties.getVersion(), properties.getHostname(),
                    properties.getMaxConcurrency(), properties.getHeartBeatIntervalSecs(),
                    properties.getMqRequestExchange(), properties.getMqRequestRouteKey());

            String workerSchema = schemaLoader.schemaLoader("schema/cron_event_request_v1.json");

            ResponseEntity<OrchestratorRegisterResponse> orchestratorRegisterResponse = client.post()
                    .uri(URI.create(orchestratorUrl + "/api/worker/register"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new OrchestratorRegisterRequest(properties.getInstanceId(), properties.getType(),
                            properties.getVersion(), properties.getHostname(), properties.getMaxConcurrency(),
                            properties.getHeartBeatIntervalSecs(), properties.getMqRequestExchange(), "{}",
                            properties.getMqRequestRouteKey(), workerSchema))
                    .retrieve().toEntity(OrchestratorRegisterResponse.class);

            context.initialize(orchestratorRegisterResponse.getBody());

            registered = true;
            logger.info(
                    "orchestrator registration successful : workerid {} workername {} statusExchange {} resultExchange {}",
                    context.getWorkerId(), context.getWorkerName(), context.getStatusExchange(),
                    context.getResultExchange());
        } catch (Exception ex) {
            logger.error("orchestrator registration failed", ex);
            executorService.schedule((() -> registerWorker()), 60, TimeUnit.SECONDS);
        }

    }

    public void sentWorkerHeartbeat() {
        executorService.scheduleWithFixedDelay(() -> {
            if (registered) {
                sentHeartBeat();
            }

        }, 10, properties.getHeartBeatIntervalSecs(), TimeUnit.SECONDS);
    }

    public void sentHeartBeat() {
        try {
            String heartbeatUrl = "/api/worker/" + properties.getInstanceId() + "/heartbeat";
            client.post().uri(URI.create(orchestratorUrl + heartbeatUrl)).retrieve().toBodilessEntity();
            logger.info("heartbeat ..|!!|..");
        } catch (Exception ex) {
            logger.error("heart beat failed", ex);
            registered = false;
            registerWorker();
        }

    }

    @PostConstruct
    public void start() {
        registerWorker();
        sentWorkerHeartbeat();
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }

}
