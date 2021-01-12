package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;

import java.util.Map;

public interface EmployeeRepository {

    Map<String, Employee> findAll();

    void saveAll(Map<String, Employee> employees);

    void addEmployee(Employee employee);

    Employee findEmployee(String id);

    void removeEmployee(String id);

    void addVacationToEmployee(String employeeId, Vacation vacation);

    void removeVacation(String id, String employeeId);

}
