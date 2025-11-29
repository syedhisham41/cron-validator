package com.syedhisham41.cron_validator.Service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.text.ParseException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.syedhisham41.cron_validator.Utils.CronUtils;

@ExtendWith(MockitoExtension.class)
public class CronQuartzServiceTest {

    MockedStatic<CronUtils> mockedUtils;

    CronQuartzService service;

    @BeforeEach
    void mockUtils() {
        service = new CronQuartzService();
        mockedUtils = Mockito.mockStatic(CronUtils.class);
        mockedUtils.when(() -> CronUtils.generateTextFromCronFields(any(), anyString()))
                .thenReturn("dummy");
    }

    @AfterEach
    void close() {
        mockedUtils.close();
    }

    static Stream<String> validCrons() {
        return Stream.of("* * * * * ?",
                "0 * * * * ?",
                "0 0 * * * ?",
                "0 0 12 * * ?",
                "0 0/5 * * * ?",
                "0 0 8 L * ?",
                "0 0 8 LW * ?",
                "0 0 9 ? * MON",
                "0 0 18 ? * MON-FRI",
                "0 30 10 ? * MON#1",
                "0 30 10 ? * FRI#2",
                "0 0/5 8-18 * * ?",
                "0 0/10 9-17 ? * MON-FRI",
                "0 0 10 1 JAN ?",
                "0 0 12 * * ? 2025",
                "0 0 0 L-2 * ?",
                "0 0 12 ? * 4L",
                "0 50 6 LW 3 ?");
    }

    static Stream<String> invalidCrons() {
        return Stream.of(
                "* * * * 5", // wrong field count
                "0 0 0 32 * ?", // day of month > 31
                "0 0 25 * * ?", // hour > 23
                "0 61 * * * ?", // minute > 59
                "0 0 10 ? * MON#6", // invalid nth weekday
                "0 0 10 ? * FRI#0", // invalid #0
                "0 0 10 ? * ABC", // invalid weekday
                "invalid-cron" // random text);
        );
    }

    @ParameterizedTest
    @MethodSource("validCrons")
    public void shouldReturnTrue_whenCronIsValid(String cronExpr) throws ParseException {
        assertTrue(service.validate(cronExpr));
    }

    @ParameterizedTest
    @MethodSource("invalidCrons")
    public void shouldReturnFalse_whenCronIsInvalid(String cronExpr) {
        assertFalse(service.validate(cronExpr));
    }

    @ParameterizedTest
    @MethodSource("validCrons")
    public void shouldReturnSummary_whenCronIsValid(String cronExpr) throws ParseException {
        assertNotNull(service.parseCronToText(cronExpr));
    }

    @ParameterizedTest
    @MethodSource("invalidCrons")
    public void shouldThrowException_whenCronIsInvalid(String cronExpr) {
        assertThrows(ParseException.class, () -> service.parseCronToText(cronExpr));
    }

    @ParameterizedTest
    @MethodSource("validCrons")
    public void shouldReturnReadableText_whenCronIsValid(String cronExpr)
            throws IllegalArgumentException, IllegalAccessException, ParseException {
        assertTrue(!service.cronToText(cronExpr).isBlank());
    }

    @ParameterizedTest
    @MethodSource("invalidCrons")
    public void shouldThrowException_whenCronIsInvalidForcronToTextAPI(String cronExpr) {
        assertThrows(ParseException.class, () -> service.cronToText(cronExpr));
    }
}
