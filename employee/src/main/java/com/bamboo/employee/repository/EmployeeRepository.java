package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;

import java.util.Collection;
import java.util.Map;

public interface EmployeeRepository {

    Map<Integer, Employee> findAll(int numberOfObjectsInFile, String fileName);

    void saveAll(Map<Integer, Employee> employees, String fileName);

}
