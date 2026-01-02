package com.syedhisham41.cron_validator.Events.Publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.syedhisham41.cron_validator.DTO.CronEventStatusResponse;
import com.syedhisham41.cron_validator.Service.WorkerRegistrationContext;

@Component
public class StatusEventPublisher {

    private final WorkerRegistrationContext context;

    private final RabbitTemplate rabbitTemplate;

    public StatusEventPublisher(RabbitTemplate rabbitTemplate, WorkerRegistrationContext context) {
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }

    // public void publishStatus(CronEventStatusResponse status) {
    // rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.STATUS_KEY,
    // status);
    // }

    public void publishStatus(CronEventStatusResponse status) {
        rabbitTemplate.convertAndSend(context.getStatusExchange(), context.getStatusRoutingKey(), status);
    }
}
