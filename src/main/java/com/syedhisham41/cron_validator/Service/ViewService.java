package com.syedhisham41.cron_validator.Service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

import com.syedhisham41.cron_validator.DTO.CronRequest;

@Service
public class ViewService {

    private final CronService service;

    public ViewService(CronService service) {
        this.service = service;
    }

    public String validateCronExpression(CronRequest cronRequest) throws ParseException {
        return service.validate(cronRequest.getCronExpr()) ? "valid " : "invalid";
    }

    public String processCronExpression(CronRequest cronRequest)
            throws ParseException, IllegalArgumentException, IllegalAccessException {
        return service.cronToText(cronRequest.getCronExpr());
    }
}
