package com.syedhisham41.cron_validator.InstanceIdentity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InstanceIdentity {

    private static final Logger logger = LoggerFactory.getLogger(InstanceIdentity.class);

    @Value("${worker.instanceId:#{null}}")
    private String instanceIdOverride;

    @Value("${worker.instanceId.file:/data/instance.id}")
    private String instanceIdFile;

    private final AtomicReference<String> cached = new AtomicReference<>();

    public String getWorkerInstanceId() {
        String current = cached.get();
        if (current != null && !current.isBlank()) {
            return current;
        }
        synchronized (this) {
            // Double-check
            current = cached.get();
            if (current != null && !current.isBlank()) {
                return current;
            }
            String resolved = resolveOrCreate();
            cached.set(resolved);
            return resolved;
        }
    }

    private String resolveOrCreate() {
        // 1) Explicit override wins (debug/admin)
        if (instanceIdOverride != null && !instanceIdOverride.isBlank()) {
            return instanceIdOverride.trim();
        }

        Path path = Paths.get(instanceIdFile);
        try {
            // 2) Reuse persisted id
            if (Files.exists(path)) {
                String id = Files.readString(path, StandardCharsets.UTF_8).trim();
                if (!id.isBlank()) {
                    logger.info("Loaded persisted worker instanceId from {}", path);
                    return id;
                }
            }

            // 3) Generate and persist once
            String generated = UUID.randomUUID().toString();
            Files.createDirectories(path.getParent());
            Files.writeString(path, generated, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            logger.info("Generated and persisted new worker instanceId at {}", path);
            return generated;
        } catch (IOException e) {
            // 4) Last resort: ephemeral (log loudly)
            String fallback = UUID.randomUUID().toString();
            logger.warn("Failed to read/write {}. Using ephemeral instanceId={} (will change on restart). Cause={}",
                    path, fallback, e.toString());
            return fallback;
        }
    }

    public String getWorkerHostName() throws UnknownHostException {
        String hostName = System.getenv("HOSTNAME");

        if (hostName.isBlank() || hostName.isEmpty()) {
            hostName = InetAddress.getLocalHost().getHostName();
        }

        logger.info("cron-validator hostname : ", hostName);
        return hostName;
    }

}
