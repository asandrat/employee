package com.bamboo.employee;

import com.bamboo.employee.service.CustomValidator;
import com.bamboo.employee.service.command.ActionExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class CommandLineAppRunner implements CommandLineRunner {
    private final ActionExecutor actionExecutor;

    public CommandLineAppRunner(ActionExecutor actionExecutor) {
        this.actionExecutor = actionExecutor;
    }

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(final String... args) {
        LOGGER.info("Application context started with command line arguments: "
                + Arrays.toString(args));

        if (!CustomValidator.validateNumberOfArguments(args)) {
            throw new IllegalArgumentException();
        }

        if (!CustomValidator.validateArguments(args)) {
            throw new IllegalArgumentException();
        }
        String[] arguments = Arrays.stream(args)
                .skip(1).toArray(String[]::new);

        Map<String, String> data = InputParser.parseInput(arguments);

        //now we already know that action is valid

        actionExecutor.executeAction(args[0],data);

    }
}
