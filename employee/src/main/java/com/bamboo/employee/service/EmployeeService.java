package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;

import java.util.Map;

public interface EmployeeService {
    void addEmployee(String name, String surname);

    void removeEmployee(String id);

    void addVacation(String id, String employeeId, String dateFrom, String dateTo,
                     String status);

    void removeVacation(String id, String employeeId);

    void approveVacation(String id, String employeeId, String status);

    void rejectVacation(String id, String employeeId, String status);

    Map<String, Employee> findAll();

    void saveAll(Map<String, Employee> map);

}
