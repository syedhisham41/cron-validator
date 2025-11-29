package com.syedhisham41.cron_validator.Utils;

import com.syedhisham41.cron_validator.DTO.CronFields;
import com.syedhisham41.cron_validator.Exceptions.DangerousCronException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CronUtilsTest {

    private CronFields build(
            String sec, String min, String hr,
            String dom, String nwdays,
            String months, String dow, String ldow) {
        CronFields f = new CronFields();
        f.setSeconds(sec);
        f.setMinutes(min);
        f.setHours(hr);
        f.setDaysOfMonth(dom);
        f.setNearestWeekdays(nwdays);
        f.setMonths(months);
        f.setDaysOfWeek(dow);
        f.setLastDayOfWeek(ldow);
        return f;
    }

    // ---------------------------------------------------------
    // TEST 1: Simple cron — "0 30 9 * * ?"
    // ---------------------------------------------------------
    @Test
    void testSimpleEverydayAt930() throws Exception {

        CronFields f = build(
                "0",
                "30",
                "9",
                "*",
                "",
                "*",
                "?",
                "");

        String cron = "0 30 9 * * ?";

        String result = CronUtils.generateTextFromCronFields(f, cron);

        assertEquals("at 0 seconds past the minute every day at 9:30 AM every day  every month",
                result);
    }

    // ---------------------------------------------------------
    // TEST 2: Step minutes + single hour
    // "0 */5 14 * * ?"
    // ---------------------------------------------------------
    @Test
    void testStepMinutes() throws Exception {

        CronFields f = build(
                "0",
                "0,5,10,15,20",
                "14",
                "*",
                "",
                "*",
                "?",
                "");

        String cron = "0 */5 14 * * ?";

        String result = CronUtils.generateTextFromCronFields(f, cron);

        assertEquals(
                "at 0 seconds past the minute every 5 minutes during 2 PM every day  every month",
                result);
    }

    // ---------------------------------------------------------
    // TEST 3: Hour range 9–17
    // "0 0 9-17 * * ?"
    // ---------------------------------------------------------
    @Test
    void testHourRange() throws Exception {
        CronFields f = build(
                "0",
                "0",
                "9,10,11,12,13,14,15,16,17",
                "*",
                "",
                "*",
                "?",
                "");

        String cron = "0 0 9-17 * * ?";

        String result = CronUtils.generateTextFromCronFields(f, cron);

        assertEquals(
                "at 0 seconds past the minute every hour from 9 AM to 5 PM on the hour every day  every month",
                result);
    }

    // ---------------------------------------------------------
    // TEST 4: Last weekday of month — "0 0 9 LW * ?"
    // ---------------------------------------------------------
    @Test
    void testLastWeekdayOfMonth() throws Exception {
        CronFields f = build(
                "0",
                "0",
                "9",
                "62", // special encoding (internal)
                "62", // nearest weekday flag
                "*",
                "?",
                "");

        String cron = "0 0 9 LW * ?";

        String result = CronUtils.generateTextFromCronFields(f, cron);

        assertEquals(
                "at 0 seconds past the minute every day at 9:00 AM on the last weekday of the month  every month",
                result);
    }

    // ---------------------------------------------------------
    // TEST 5: DOW list — "0 0 12 ? * MON,WED,FRI"
    // ---------------------------------------------------------
    @Test
    void testDayOfWeekList() throws Exception {
        CronFields f = build(
                "0",
                "0",
                "12",
                "?",
                "",
                "*",
                "MON,WED,FRI",
                "");

        String cron = "0 0 12 ? * MON,WED,FRI";

        String result = CronUtils.generateTextFromCronFields(f, cron);

        assertEquals(
                "at 0 seconds past the minute every day at 12:00 PM  on [Monday, Wednesday, Friday] every month",
                result);
    }

    // ---------------------------------------------------------
    // TEST 6: Seconds step — should throw DangerousCronException
    // "0/2 * * * * ?" → step < 5 → unsafe
    // ---------------------------------------------------------
    @Test
    void testDangerousSecondsStep() {

        CronFields f = build(
                "0,2,4,6,8", // step = 2
                "*",
                "*",
                "*",
                "",
                "*",
                "?",
                "");

        String cron = "0/2 * * * * ?";

        assertThrows(DangerousCronException.class, () -> CronUtils.generateTextFromCronFields(f, cron));
    }

    // ---------------------------------------------------------
    // TEST 7: Month list
    // "0 0 10 * JAN,MAR,MAY ?"
    // ---------------------------------------------------------
    @Test
    void testMonthsList() throws Exception {

        CronFields f = build(
                "0",
                "0",
                "10",
                "*",
                "",
                "1,3,5",
                "?",
                "");

        String cron = "0 0 10 * JAN,MAR,MAY ?";

        String result = CronUtils.generateTextFromCronFields(f, cron);

        assertEquals(
                "at 0 seconds past the minute every day at 10:00 AM every day  at months [January, March, May]",
                result);
    }
}
