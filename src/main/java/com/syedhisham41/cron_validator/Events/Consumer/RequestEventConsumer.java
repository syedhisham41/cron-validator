package com.syedhisham41.cron_validator.Events.Consumer;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.syedhisham41.cron_validator.Constants.StatusTypes;
import com.syedhisham41.cron_validator.DTO.CronEventRequest;
import com.syedhisham41.cron_validator.DTO.CronEventResponse;
import com.syedhisham41.cron_validator.DTO.CronEventStatusResponse;
import com.syedhisham41.cron_validator.Events.Config.RabbitConfig;
import com.syedhisham41.cron_validator.Events.Publisher.ResultsEventPublisher;
import com.syedhisham41.cron_validator.Events.Publisher.StatusEventPublisher;
import com.syedhisham41.cron_validator.Service.CronService;
import com.syedhisham41.cron_validator.Service.WorkerRegistrationContext;

@Component
public class RequestEventConsumer {

        private final ResultsEventPublisher resultPublisher;

        private final StatusEventPublisher statusPublisher;

        private final Map<String, CronService> parserServices;

        private final WorkerRegistrationContext context;

        public RequestEventConsumer(ResultsEventPublisher resultPublisher,
                        StatusEventPublisher statusPublisher, Map<String, CronService> parserServices,
                        WorkerRegistrationContext context) {
                this.resultPublisher = resultPublisher;
                this.statusPublisher = statusPublisher;
                this.parserServices = parserServices;
                this.context = context;
        }

        @RabbitListener(queues = RabbitConfig.REQUEST_QUEUE, concurrency = "3-10")
        public void validateCronEventRequest(CronEventRequest request) {

                CronService service = parserServices.get(request.getCronType().toString());

                statusPublisher.publishStatus(new CronEventStatusResponse(request.getJobId(), request.getRequestId(),
                                StatusTypes.STARTED, LocalDateTime.now(), context.getWorkerId().toString()));

                statusPublisher.publishStatus(new CronEventStatusResponse(request.getJobId(), request.getRequestId(),
                                StatusTypes.IN_PROGRESS, LocalDateTime.now(), context.getWorkerId().toString()));

                try {

                        boolean valid = service.validate(request.getCronValue());

                        String textSummary = service.cronToText(request.getCronValue());

                        statusPublisher.publishStatus(
                                        new CronEventStatusResponse(request.getJobId(), request.getRequestId(),
                                                        StatusTypes.COMPLETED, LocalDateTime.now(),
                                                        context.getWorkerId().toString()));

                        resultPublisher.publishResult(
                                        new CronEventResponse(request.getJobId(), request.getRequestId(), valid,
                                                        textSummary, null, LocalDateTime.now()));

                } catch (Exception ex) {
                        statusPublisher.publishStatus(
                                        new CronEventStatusResponse(request.getJobId(), request.getRequestId(),
                                                        StatusTypes.FAILED, LocalDateTime.now(),
                                                        context.getWorkerId().toString()));

                        resultPublisher.publishResult(
                                        new CronEventResponse(request.getJobId(), request.getRequestId(), false, null,
                                                        ex.getMessage(), LocalDateTime.now()));
                }

        }
}
