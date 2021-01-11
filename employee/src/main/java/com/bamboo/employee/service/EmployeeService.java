package com.bamboo.employee.service;

public interface EmployeeService {
    void addEmployee(String name, String surname);

    void removeEmployee(String employeeId);

    void addVacation(
            String employeeId,
            String dateFrom,
            String dateTo,
            String status);

    void removeVacation(String vacationId);

    void approveVacation(String vacationId);

    void rejectVacation(String vacationId);
}
