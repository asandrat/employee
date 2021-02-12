package com.bamboo.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class EmployeeApplication {


    public static void main(final String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Configuration
    @ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
    @EnableScheduling
    static class SchedulingConfiguration {
    }
}
