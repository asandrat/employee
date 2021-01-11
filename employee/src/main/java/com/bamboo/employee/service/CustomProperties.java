package com.bamboo.employee.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "employees")
public class CustomProperties {
    private final String data = "target/employees.json";

    public String getData() {
        return data;
    }
}
