package com.bamboo.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.CustomValidator;
import com.bamboo.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class CommandLineAppRunner implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

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

        //read from file:
        Map<String, Employee> mapRead = employeeService.findAll();
        for (String id : mapRead.keySet()) {
            System.out.println(id + " " + mapRead.get(id).getName() + " " + mapRead.get(id).getSurname());
        }

        System.out.println("Changed map---------------- ");

        Map<String, Employee> mapRead1 = employeeService.findAll();
        for (String id : mapRead1.keySet()) {
            System.out.println(id + " " + mapRead1.get(id).getName() + " " + mapRead1.get(id).getSurname());
            if (mapRead1.get(id).getVacations().size() > 0) {
                System.out.println(mapRead1.get(id).getVacations().get(0).getStatus());
            }
        }
    }
}
