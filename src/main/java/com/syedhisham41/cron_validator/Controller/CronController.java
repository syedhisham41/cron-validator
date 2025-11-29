package com.syedhisham41.cron_validator.Controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syedhisham41.cron_validator.DTO.CronRequest;
import com.syedhisham41.cron_validator.Service.CronService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class CronController {

        private final Map<String, CronService> parserServices;

        public CronController(Map<String, CronService> parserServices) {
                this.parserServices = parserServices;
        }

        @Operation(summary = "Validate cron expression")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cron is valid", content = @Content(schema = @Schema(implementation = Boolean.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid cron expression", content = @Content)
        })
        @PostMapping("/validate")
        public boolean validate(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cron expression and type", required = true, content = @Content(schema = @Schema(implementation = CronRequest.class), examples = {
                                        @ExampleObject(name = "QuartzCronExample", value = "{\"cronExpr\":\"0 0/5 * * * ?\",\"cronType\":\"QUARTZ\"}")
                        })) @RequestBody CronRequest cronRequest)
                        throws ParseException {
                return parserServices.get(cronRequest.getCronType().toString()).validate(cronRequest.getCronExpr());
        }

        @Operation(summary = "Convert cron expression to human-readable text")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Human-readable cron text", content = @Content(schema = @Schema(implementation = String.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid cron expression", content = @Content)
        })
        @PostMapping("/crontext")
        public String cronToText(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cron expression and type", required = true, content = @Content(schema = @Schema(implementation = CronRequest.class), examples = {
                                        @ExampleObject(name = "QuartzCronExample", value = "{\"cronExpr\":\"0 0 12 * * ?\",\"cronType\":\"QUARTZ\"}")
                        })) @RequestBody CronRequest cronRequest)
                        throws ParseException, IllegalArgumentException, IllegalAccessException {
                return parserServices.get(cronRequest.getCronType().toString()).cronToText(cronRequest.getCronExpr());
        }

        @Operation(summary = "Test parsing cron expression to text")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Parsed cron text", content = @Content(schema = @Schema(implementation = String.class), examples = {
                                        @ExampleObject(name = "QuartzCronExample", value = "{\"cronExpr\":\"0 15 10 ? * MON-FRI\",\"cronType\":\"QUARTZ\"}")
                        })),
                        @ApiResponse(responseCode = "400", description = "Invalid cron expression", content = @Content)
        })
        @PostMapping("/crontexttest")
        public String cronToText2(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cron expression and type", required = true, content = @Content(schema = @Schema(implementation = CronRequest.class))) @RequestBody CronRequest cronRequest)
                        throws ParseException {
                return parserServices.get(cronRequest.getCronType().toString())
                                .parseCronToText(cronRequest.getCronExpr());
        }
}
