package com.bamboo.employee.repository.employee;

import com.bamboo.employee.entities.Employee;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface EmployeeRepository {

    Map<String, Employee> findAll();

    void saveAll(Map<String, Employee> employees);

    void addEmployee(Employee employee);

    Employee findEmployee(String id);

    void removeEmployee(String id);

    boolean isFileEmpty(File file) throws IOException;


}
