package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private Map<String, Employee> employeeList;

    public EmployeeRepositoryImpl() {
        employeeList = findAll();
        if (employeeList == null) {
            employeeList = new HashMap<>();
        }
    }

    @Override
    public Map<String, Employee> findAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            employeeList = objectMapper.readValue(
                    new File("target/employees.json"),
                    new TypeReference<Map<String, Employee>>() {
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    @Override
    public void saveAll(Map<String, Employee> employees) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(
                    new File("target/employees.json"),
                    employeeList
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEmployee(Employee emp) {
        employeeList.put(emp.getUniqueId(), emp);
        saveAll(employeeList);
    }

    @Override
    public Employee findEmployee(String uniqueId) {
        return employeeList.get(uniqueId);
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeeList.remove(employeeId);
        saveAll(employeeList);
    }

    @Override
    public void addVacationToEmployee(String employeeId, Vacation vacation) {
        Employee employee = findEmployee(employeeId);
        employee.getVacations().add(vacation);
        saveAll(employeeList);
    }
}
