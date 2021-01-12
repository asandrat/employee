package com.bamboo.employee;

import com.bamboo.employee.service.*;
import com.bamboo.employee.service.argumentparser.ArgumentParser;
import com.bamboo.employee.service.commandstrategy.CommandInvoker;
import com.bamboo.employee.service.validationstrategy.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class CommandLineAppRunner implements CommandLineRunner {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private Validator validator;
    @Autowired
    private CommandInvoker commandInvoker;

    private void guideForSupportedCommands(final String command) {
        System.out.println(
                "Seems like you passed unsupported command: " + command);
        System.out.println("Use help to see all supported commands");
    }

    private void runCommand(final String... args) {
        try {
            String[] inputArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
            Map<String, String> commandArgs = ArgumentParser.parseData(inputArgs);
            Map<String, String> validatedCommandArgs = validator.validateAndRemoveRedundantArgs(args[0], commandArgs);
            commandInvoker.executeCommand(args[0], validatedCommandArgs);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad input");
            System.exit(64);
        }
    }

    @Override
    public void run(final String... args) {
        LOGGER.info(
                "Application context started with command line arguments: "
                        + Arrays.toString(args));

        if (SupportedCommands.isSupportedCommand(args[0])) {
            runCommand(args);
        } else {
            guideForSupportedCommands(args[0]);
        }
    }

}
