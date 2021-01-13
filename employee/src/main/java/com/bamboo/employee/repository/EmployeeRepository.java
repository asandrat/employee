package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;

import java.util.Map;

public interface EmployeeRepository {
    Map<String, Employee> findAll();

    void saveAll(Map<String, Employee> employees);

    void addEmployee(Employee emp);

    Employee findEmployee(String uniqueId);

    void deleteEmployee(String employee);

    void addVacationToEmployee(String employeeId, Vacation vacation);

    void removeVacation(String vacationId, String employeeUniqueId);

    void approveVacation(String vacationId, String employeeUniqueId);

    void rejectVacation(String vacationId, String employeeUniqueId);
}
