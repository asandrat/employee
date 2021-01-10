package com.bamboo.employee;

import com.bamboo.employee.service.CustomValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandLineAppRunner implements CommandLineRunner {
    @Value("${spring.file.name}")
    private String fileName;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(final String... args) {
        LOGGER.info("Application context started with command line arguments: "
                + Arrays.toString(args));
        System.out.println("file name:" + fileName);

        if (!CustomValidator.validateNumberOfArguments(args)) {
            throw new IllegalArgumentException();
        }

        if (!CustomValidator.validateAction(args[0])) {
            throw new IllegalArgumentException();
        }

        //to do: read from file, write in map, work with map, write in file
    }
}
