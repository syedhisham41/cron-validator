package com.syedhisham41.cron_validator.Events.Publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.syedhisham41.cron_validator.DTO.CronEventStatusResponse;
import com.syedhisham41.cron_validator.Events.Config.RabbitConfig;

@Component
public class StatusEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public StatusEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishStatus(CronEventStatusResponse status) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.STATUS_KEY, status);
    } 
}
