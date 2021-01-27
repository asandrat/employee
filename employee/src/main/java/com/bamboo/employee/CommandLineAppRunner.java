package com.bamboo.employee;

import com.bamboo.employee.service.*;
import com.bamboo.employee.service.argumentparser.ArgumentParser;
import com.bamboo.employee.service.commandstrategy.CommandInvoker;
import com.bamboo.employee.service.validationstrategy.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnProperty(value="spring.main.web-application-type", havingValue = "none")
public class CommandLineAppRunner implements CommandLineRunner {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CommandLineAppRunner.class);

    private final ArgumentParser parser;
    private final Validator validator;
    private final CommandInvoker commandInvoker;

    public CommandLineAppRunner(final ArgumentParser parser,
                         final Validator validator,
                         final CommandInvoker commandInvoker) {
        this.parser = parser;
        this.validator = validator;
        this.commandInvoker = commandInvoker;
    }

    private void guideForSupportedCommands(final String command) {
        System.out.println(
                "Seems like you passed unsupported command: " + command);
        System.out.println("Use help to see all supported commands");
    }

    private void runCommand(final String... args) {
        try {
            String[] inputArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
            Map<String, String> commandArgs = parser.parseData(inputArgs);
            validator.validate(args[0], commandArgs);

            Map<String, String> emp = new HashMap<>();
            emp.put("uniqueId", "1");
            emp.put("name", "Petar");
            emp.put("surname", "Kosanin");

            commandInvoker.executeCommand("employee_addition", emp);

            commandInvoker.executeCommand(args[0], commandArgs);

            Map<String, String> xs = new HashMap<>();
            xs.put("uniqueId", "1");
            xs.put("employeeUniqueId", "1");

            commandInvoker.executeCommand("get_employee", xs);
            commandInvoker.executeCommand("vacation_rejection", xs);
            commandInvoker.executeCommand("get_employee", xs);
            commandInvoker.executeCommand("vacation_approval", xs);

            commandInvoker.executeCommand("get_employee", xs);
            commandInvoker.executeCommand("vacation_removal", xs);


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
