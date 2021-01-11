package com.bamboo.employee;

import com.bamboo.employee.service.*;
import com.bamboo.employee.service.command.CommandInvoker;
import com.bamboo.employee.service.command.RemoveEmployeeCommand;
import com.bamboo.employee.service.validationstrategy.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLineAppRunner implements CommandLineRunner {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Autowired
    private EmployeeService service;

    private void guideForSupportedCommands(final String command) {
        System.out.println(
                "Seems like you passed unsupported command: " + command);
        System.out.println("Use help to see all supported commands");
    }

    @Override
    public void run(final String... args) {
        LOGGER.info(
                "Application context started with command line arguments: "
                        + Arrays.toString(args));


        Validator validator = new Validator();
        CommandInvoker invoker = new CommandInvoker();
        if (SupportedCommands.isSupportedCommand(args[0])) {
            String[] namedArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
            Map<String, String> data = ArgumentParser.parseData(namedArgs);
            validator.setStrategy(ValidationStrategyPicker.pickStrategy(args[0]));
            try {
                data = validator.validateAndRemoveRedundantArgs(data);
                invoker.setCommand(CommandPicker.pickCommand(args[0], service, data));
                invoker.executeCommand();

            } catch (IllegalArgumentException e) {
                System.out.println("Bad input");
                System.exit(1);
            }
        } else {
            guideForSupportedCommands(args[0]);
        }
    }

}
