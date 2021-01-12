package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private Map<Integer, Employee> employeeMap;

    public EmployeeRepositoryImpl() {
        //to do: hard-coded:
        employeeMap = findAll(3, "employees.txt"); //
    }

    @Override
    public Map<Integer,Employee> findAll(int objectsInFile, String fileName) {
        //read from file
        Map<Integer, Employee> map = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);
            for (int i = 0; i < objectsInFile; i++) {
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

    @Override
    public void saveAll(Map<Integer, Employee> employeeMap, String fileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            for (Integer id : employeeMap.keySet()) {
                objectOutputStream.writeObject(employeeMap.get(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
