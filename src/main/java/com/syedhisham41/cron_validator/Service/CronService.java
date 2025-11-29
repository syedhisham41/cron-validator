package com.syedhisham41.cron_validator.Service;

import java.text.ParseException;

public interface CronService {

    public boolean validate(String cronExpr) throws ParseException;

    public String cronToText(String cronExpr) throws ParseException, IllegalArgumentException, IllegalAccessException;

    public String parseCronToText(String cronExpr) throws ParseException;

}
