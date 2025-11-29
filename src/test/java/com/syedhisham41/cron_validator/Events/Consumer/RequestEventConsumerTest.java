package com.syedhisham41.cron_validator.Events.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.syedhisham41.cron_validator.Constants.CronType;
import com.syedhisham41.cron_validator.Constants.StatusTypes;
import com.syedhisham41.cron_validator.DTO.CronEventRequest;
import com.syedhisham41.cron_validator.DTO.CronEventResponse;
import com.syedhisham41.cron_validator.DTO.CronEventStatusResponse;
import com.syedhisham41.cron_validator.Events.Publisher.ResultsEventPublisher;
import com.syedhisham41.cron_validator.Events.Publisher.StatusEventPublisher;
import com.syedhisham41.cron_validator.Service.CronQuartzService;
import com.syedhisham41.cron_validator.Service.CronService;

@ExtendWith(MockitoExtension.class)
class RequestEventConsumerTest {

    @Mock
    private CronQuartzService quartzService;

    @Mock
    private ResultsEventPublisher resultPublisher;

    @Mock
    private StatusEventPublisher statusPublisher;

    private RequestEventConsumer consumer;

    @BeforeEach
    void setup() {
        // prepare parserServices map
        Map<String, CronService> parserMap = Map.of(
                CronType.QUARTZ.toString(), quartzService
        );

        consumer = new RequestEventConsumer(resultPublisher, statusPublisher, parserMap);
    }

    @Test
    void validateCronEventRequestTest() throws Exception {

        CronEventRequest request = new CronEventRequest(
                "job-1",
                "0 0/5 * * * ?",
                CronType.QUARTZ,
                "req-1",
                Instant.parse("2024-01-01T00:00:00Z")
        );

        Mockito.when(quartzService.validate(request.getCronValue()))
               .thenReturn(true);

        Mockito.when(quartzService.cronToText(request.getCronValue()))
               .thenReturn("dummy-summary");

        consumer.validateCronEventRequest(request);

        // Capture status events
        ArgumentCaptor<CronEventStatusResponse> statusCaptor =
                ArgumentCaptor.forClass(CronEventStatusResponse.class);

        Mockito.verify(statusPublisher, Mockito.times(3))
               .publishStatus(statusCaptor.capture());

        // Check statuses
        List<CronEventStatusResponse> statuses = statusCaptor.getAllValues();
        assertEquals(StatusTypes.STARTED, statuses.get(0).getStatusType());
        assertEquals(StatusTypes.IN_PROGRESS, statuses.get(1).getStatusType());
        assertEquals(StatusTypes.COMPLETED, statuses.get(2).getStatusType());

        // Capture final result
        ArgumentCaptor<CronEventResponse> resultCaptor =
                ArgumentCaptor.forClass(CronEventResponse.class);

        Mockito.verify(resultPublisher)
               .publishResult(resultCaptor.capture());

        CronEventResponse result = resultCaptor.getValue();

        assertEquals(true, result.isValid());
        assertEquals("dummy-summary", result.getSummary());
        assertNull(result.getErrorMessage());
    }
}
