package com.syedhisham41.cron_validator.Service;

import java.text.ParseException;

import org.quartz.CronExpression;
import org.springframework.stereotype.Service;

import com.syedhisham41.cron_validator.DTO.CronFields;
import com.syedhisham41.cron_validator.Utils.CronUtils;

@Service("QUARTZ")
public class CronQuartzService implements CronService {

    @Override
    public boolean validate(String cronExpr) {
        try {
            CronExpression.validateExpression(cronExpr);
            return true;
        } catch (ParseException ex) {
            System.out.println("validate cron failed : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public String cronToText(String cronExpr) throws ParseException, IllegalArgumentException, IllegalAccessException {
        CronFields fields = populateCronFields(cronExpr);
        return CronUtils.generateTextFromCronFields(fields, cronExpr);
    }

    @Override
    public String parseCronToText(String cronExpr) throws ParseException {
        CronExpression cron = new CronExpression(cronExpr);
        return cron.getExpressionSummary();
    }

    private static CronFields populateCronFields(String cronExpr) throws ParseException {
        CronExpression cron = new CronExpression(cronExpr);
        CronFields fields = new CronFields();

        for (String line : cron.getExpressionSummary().split("\n")) {

            String[] parts = line.split(":");
            if (parts.length < 2)
                continue;

            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (key) {
                case "seconds" -> fields.setSeconds(value);
                case "minutes" -> fields.setMinutes(value);
                case "hours" -> fields.setHours(value);
                case "daysOfMonth" -> fields.setDaysOfMonth(value);
                case "nearestWeekdays" -> fields.setNearestWeekdays(value);
                case "months" -> fields.setMonths(value);
                case "daysOfWeek" -> fields.setDaysOfWeek(value);
                case "lastDayOfWeek" -> fields.setLastDayOfWeek(value);
                case "NthDayOfWeek" -> fields.setNthDayOfWeek(value);
                case "years" -> fields.setYears(value);
            }
        }

        return fields;
    }

}
