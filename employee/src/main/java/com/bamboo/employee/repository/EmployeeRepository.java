package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;

import java.util.Map;

public interface EmployeeRepository {

    Map<Integer, Employee> findAll();

    void saveAll(Map<Integer, Employee> employees);

    void addEmployee(Employee employee);

    Employee findEmployee(int id);

    void removeEmployee(int id);

    void addVacationToEmployee(int employeeId, Vacation vacation);

    void removeVacation(int id, int employeeId);

}
