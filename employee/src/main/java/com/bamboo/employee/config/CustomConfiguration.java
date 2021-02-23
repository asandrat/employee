package com.bamboo.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class CustomConfiguration {


    @Bean
    public ExecutorService executorService(
            @Value("${number.of.threads}") int numberOfThreads
    ) {
        return Executors.newFixedThreadPool(numberOfThreads);
    }
}
