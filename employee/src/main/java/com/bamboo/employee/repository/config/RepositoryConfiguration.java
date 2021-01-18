package com.bamboo.employee.repository.config;

import com.bamboo.employee.repository.EmployeeRepository;
import com.bamboo.employee.repository.EmployeeRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public EmployeeRepository employeeRepository(
            @Value("${spring.employeeApp.file.path}") String fileName,
            ObjectMapper objectMapper
    ) {
        return new EmployeeRepositoryImpl(fileName, objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
