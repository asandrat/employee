package com.bamboo.employee.service;

public interface EmployeeService {
    void addEmployee(String name, String surname);

    void removeEmployee(String employeeId);

    void addVacation(
            String employeeId,
            String dateFrom,
            String dateTo,
            String status);

    void removeVacation(String vacationId, String employeeUniqueId);

    void approveVacation(String vacationId, String employeeUniqueId);

    void rejectVacation(String vacationId, String employeeUniqueId);

}
