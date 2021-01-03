package com.bamboo.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandLineAppRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(String... args) {
        LOGGER.info("Application context started with command line arguments: " + Arrays.toString(args));
    }
}
