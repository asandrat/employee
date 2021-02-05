package com.bamboo.employee.repositoryFile.employee;

import com.bamboo.employee.entitiesFile.EmployeeFile;

import java.util.Map;
import java.util.Optional;

public interface EmployeeRepository {

    Map<String, EmployeeFile> findAll();

    void saveAll(Map<String, EmployeeFile> employees);

    void addEmployee(EmployeeFile employee);

    EmployeeFile findEmployee(String id);

    Optional<EmployeeFile> removeEmployee(String id);

}
