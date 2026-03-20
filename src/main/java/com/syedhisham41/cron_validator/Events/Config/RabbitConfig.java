package com.syedhisham41.cron_validator.Events.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

    // Exchange
    public static final String EXCHANGE = "worker.cron.validator.exchange";

    // Queues
    public static final String REQUEST_QUEUE = "worker.cron.validator.requests.queue";
    public static final String RESULTS_QUEUE = "worker.cron.validator.results.queue";
    public static final String STATUS_QUEUE = "worker.cron.validator.status.queue";

    // Routing Keys
    public static final String REQUEST_KEY = "cron.request";
    public static final String RESULT_KEY = "cron.result";
    public static final String STATUS_KEY = "cron.status";

    @Bean
    public TopicExchange cronExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue requestsQueue() {
        return new Queue(REQUEST_QUEUE, true);
    }

    @Bean
    public Binding bindRequestQueue() {
        return BindingBuilder.bind(requestsQueue()).to(cronExchange()).with(REQUEST_KEY);
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
