package com.bamboo.employee;

import com.bamboo.employee.entities.UserAction;
import com.bamboo.employee.parser.InputParser;
import com.bamboo.employee.service.command.ActionProcessor;
import com.bamboo.employee.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@ConditionalOnProperty(value="spring.main.web-application-type", havingValue = "none")
public class CommandLineAppRunner implements CommandLineRunner {

    private final Validator validator;
    private final ActionProcessor processor;

    public CommandLineAppRunner(
            Validator validator,
            ActionProcessor processor
    ) {
        this.validator = validator;
        this.processor = processor;
    }

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(String... args) {
        LOGGER.info("Application context started with command line " +
                "arguments:" + Arrays.toString(args));
        System.out.println(args[0]);

        String[] userInput = Arrays.stream(args)
                .skip(1)
                .toArray(String[]::new);
        Map<String, String> data = InputParser.parseData(userInput);

        if (UserAction.isValid(args[0]) && validator.validate(args[0], data)) {
            processor.process(args[0], data);
        }
        else {
            throw new IllegalArgumentException("Parameters are not valid");
        }
    }
}

