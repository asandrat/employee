package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;

import java.util.Map;

public interface EmployeeService {
    void addEmployee(int id, String name, String surname);

    void removeEmployee(int id);

    void addVacation(int id, int employeeId, String dateFrom, String dateTo,
                     String status);

    void removeVacation(int id, int employeeId);

    void approveVacation(int id, int employeeId, String status);

    void rejectVacation(int id, int employeeId, String status);

    Map<Integer, Employee> findAll();

    void saveAll(Map<Integer, Employee> map);

}
