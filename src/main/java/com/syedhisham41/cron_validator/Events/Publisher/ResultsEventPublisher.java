package com.syedhisham41.cron_validator.Events.Publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.syedhisham41.cron_validator.DTO.CronEventResponse;
import com.syedhisham41.cron_validator.Service.WorkerRegistrationContext;

@Component
public class ResultsEventPublisher {

    private final WorkerRegistrationContext context;
    private final RabbitTemplate rabbitTemplate;

    public ResultsEventPublisher(RabbitTemplate rabbitTemplate, WorkerRegistrationContext context) {
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }

    // public void publishResult(CronEventResponse response) {
    // rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.RESULT_KEY,
    // response);
    // }
    public void publishResult(CronEventResponse response) {
        rabbitTemplate.convertAndSend(context.getResultExchange(), context.getResultRoutingKey(), response);
    }
}
