package com.syedhisham41.cron_validator.Events.Publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.syedhisham41.cron_validator.DTO.CronEventResponse;
import com.syedhisham41.cron_validator.Events.Config.RabbitConfig;

@Component
public class ResultsEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ResultsEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishResult(CronEventResponse response) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.RESULT_KEY, response);
    }
}
