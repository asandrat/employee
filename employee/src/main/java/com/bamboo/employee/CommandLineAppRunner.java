package com.bamboo.employee;

import com.bamboo.employee.service.command.ActionExecutor;
import com.bamboo.employee.service.validator.CustomValidator;
import com.bamboo.employee.service.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandLineAppRunner implements CommandLineRunner {
    private final ActionExecutor actionExecutor;
    private final CustomValidator customValidator;

    public CommandLineAppRunner(ActionExecutor actionExecutor,
                                CustomValidator customValidator) {
        this.actionExecutor = actionExecutor;
        this.customValidator = customValidator;
    }

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Override
    public void run(final String... args) {
        LOGGER.info("Application context started with command line arguments: "
                + Arrays.toString(args));

        //if (UserAction.isValid(args[0]) && validator.validate(args[0],
        // data)) {
        //            processor.process(args[0], data);
        //        }
        //        else {
        //            throw new IllegalArgumentException("Parameters are not
        //            valid");
        //        }


        String[] arguments = Arrays.stream(args)
                .skip(1).toArray(String[]::new);

        Map<String, String> data = InputParser.parseInput(arguments);

        Optional<Action> action =
                Optional.ofNullable(Action.fromString(args[0]));

        if (action.isPresent() && customValidator.validate(args[0], data)) {
            actionExecutor.executeAction(args[0], data);
        } else {
            throw new IllegalArgumentException("wrong command line arguments");
        }

    }
}
