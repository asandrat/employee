package com.bamboo.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.CustomValidator;
import com.bamboo.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLineAppRunner implements CommandLineRunner {

    @Value("${spring.file.name.employees}")
    private String fileName;

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

        //map bellow is just to write to file for the first time:
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "Will", "Smith"));
        map.put(2, new Employee(2, "Jennifer", "Aniston"));
        map.put(3, new Employee(3, "Lisa", "Kudrow"));
        //write to file:
        int objectsInFile = employeeService.saveAll(map,fileName);

        //read from file:
//        Map<Integer, Employee> mapRead = employeeService.findAll(objectsInFile);
//        for (Integer id : mapRead.keySet()) {
//            System.out.println(id + " " + mapRead.get(id).getName());
//        }

        //do sth with map......


        //write to file:
//      objectsInFile = employeeService.saveAll(map);
    }
}
