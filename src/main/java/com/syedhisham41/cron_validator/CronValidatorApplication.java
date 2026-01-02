package com.syedhisham41.cron_validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CronValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronValidatorApplication.class, args);
	}

}