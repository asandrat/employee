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

    Vacation findVacation(Employee employee, String vacationId);

    void removeVacation(String vacationId, String employeeUniqueId);

    void approveVacation(Vacation vacation);

    void rejectVacation(Vacation vacation);
}
