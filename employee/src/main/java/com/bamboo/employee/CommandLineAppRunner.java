package com.bamboo.employee;

import com.bamboo.employee.service.ArgumentParser;
import com.bamboo.employee.service.EmployeeService;
import com.bamboo.employee.service.SupportedCommands;
import com.bamboo.employee.service.command.AddEmployeeCommand;
import com.bamboo.employee.service.command.CommandInvoker;
import com.bamboo.employee.service.validationstrategy.AddEmployeeValidateStrategy;
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
    private EmployeeService service;

    private void guideForSupportedCommands(final String command) {
        System.out.println(
                "Seems like you passed unsupported command: " + command);
        System.out.println("Use help to see all supported commands");
    }

    @Override
    public void run(String... args) {
        LOGGER.info(
                "Application context started with command line arguments: "
                        + Arrays.toString(args));

        /*
        readFromFile TODO
        ako je podrzana komanda onda
            parsiraj podatke -> Map<String, String>
            podesi validatora
            validiraj podatke -> Map<String, String> + izbaceni suvisni podaci
                - podesi odgovarajucu komandu TODO fix
                - izvrsi komandu
        saveToFile TODO
         */
        if (SupportedCommands.isSupportedCommand(args[0])) {
            String[] namedArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
            Map<String, String> as = ArgumentParser.parseData(namedArgs);
            Validator validator = new Validator();
            CommandInvoker invoker = new CommandInvoker();

            if (SupportedCommands.valueOf(args[0])
                    == SupportedCommands.employee_addition) {
                validator.setStrategy(new AddEmployeeValidateStrategy());
            }
            try {
                // TODO change the way ivoker command is set
                // rn, its hardcoded to AddEmployeeCommand regardless of args[0]
                as = validator.validateAndRemoveRedundantArgs(as);
                invoker.setCommand(new AddEmployeeCommand(service, as));
                invoker.executeCommand();
            } catch (IllegalArgumentException e) {
                System.out.println("Bad input");
                System.exit(64);
            }

        } else {
            guideForSupportedCommands(args[0]);
        }
    }
}
