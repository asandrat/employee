package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;

import java.util.Map;

public interface EmployeeService {
    void addEmployee(String name, String surname);

    void removeEmployee(String id);

    void saveAll(Map<String, Employee> map);

    Map<String, Employee> findAll();
}
