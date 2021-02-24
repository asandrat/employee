package com.bamboo.employee;

import com.bamboo.employee.service.validator.CustomValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(value="spring.main.web-application-type", havingValue = "none")
public class CommandLineAppRunner implements CommandLineRunner {

    private final CustomValidator customValidator; //to reduce if/else for
    // command line arguments (validation)

    public CommandLineAppRunner(CustomValidator customValidator) {
        this.customValidator = customValidator;
    }

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(final String... args) {
        LOGGER.info("Application context started with command line arguments: "
                + Arrays.toString(args));
    }
}
