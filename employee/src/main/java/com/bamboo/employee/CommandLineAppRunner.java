package com.bamboo.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.CustomValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLineAppRunner implements CommandLineRunner {
    @Value("${spring.file.name}")
    private String fileName;
    private static int objectsInFile;

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

        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "Will", "Smith"));
        map.put(2, new Employee(2, "Jennifer", "Aniston"));
        map.put(3, new Employee(3, "Lisa", "Kudrow"));
        objectsInFile = map.size();
        //do sth with map
        //writeToFile(map);
        Map<Integer, Employee> mapRead = readFromFile();
        for (Integer id : mapRead.keySet()) {
            System.out.println(id + " " + mapRead.get(id).getName());
        }
    }

    private Map<Integer, Employee> readFromFile() {
        Map<Integer, Employee> map = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);
            for (int i = 0; i < objectsInFile; i++){
                System.out.println("tu");
                Employee employee = (Employee) objectInputStream.readObject();
                System.out.println(employee.getName());
                map.put(employee.getId(), employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void writeToFile(Map<Integer, Employee> map) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            for (Integer id : map.keySet()) {
                objectOutputStream.writeObject(map.get(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
