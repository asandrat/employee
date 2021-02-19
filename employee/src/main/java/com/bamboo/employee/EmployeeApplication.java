package com.bamboo.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SchedulingConfiguration.class)
public class EmployeeApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }
}
