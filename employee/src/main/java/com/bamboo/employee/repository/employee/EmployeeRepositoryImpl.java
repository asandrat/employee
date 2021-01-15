package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Value("${spring.file.name.employees}")
    private String fileNameEmployees;

    private Map<String, Employee> employeeMap;

    @PostConstruct
    public void init() {
        System.out.println("init: " + fileNameEmployees);
        employeeMap = findAll();
    }


    @Override
    public Map<String, Employee> findAll() {
        //read from file
        Map<String, Employee> map = new HashMap<>();
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(fileNameEmployees);
            if (isFileEmpty(new File(fileNameEmployees))) {
                System.out.println("employees.txt is empty");
                return map;
            }
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);
            while (true) {
                Employee employee = (Employee) objectInputStream.readObject();
                if (employee == null) {
                    break;
                } else {
                    map.put(employee.getId(), employee);
                }
            }
        } catch (EOFException eofException) {
            eofException.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Cannot read employees file");
        }
        return map;
    }

    @Override
    public void saveAll(Map<String, Employee> employeeMap) {
        //write to file
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(fileNameEmployees);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            for (String id : employeeMap.keySet()) {
                objectOutputStream.writeObject(employeeMap.get(id));
            }
            objectOutputStream.writeObject(null);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
        saveAll(employeeMap);
    }

    @Override
    public void removeEmployee(String id) {
        employeeMap.remove(id);
        saveAll(employeeMap);
    }

    @Override
    public Employee findEmployee(String id) {
        return employeeMap.get(id);
    }

    @Override
    public boolean isFileEmpty(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine() == null;
    }

}
