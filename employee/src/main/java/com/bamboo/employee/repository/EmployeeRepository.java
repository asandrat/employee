package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface EmployeeRepository {
    Map<String, Employee> findAll();

    void saveAll(Map<String, Employee> employees);

    void addEmployee(Employee emp);

    Employee findEmployee(String uniqueId);

    void deleteEmployee(String employee);

    void addVacationToEmployee(String employeeId, Vacation vacation);
}
