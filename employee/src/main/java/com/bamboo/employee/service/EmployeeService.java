package com.bamboo.employee.service;

import com.bamboo.employee.entities.Employee;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO addEmployee(String name, String surname);

    void removeEmployee(String employeeId);

    VacationDTO addVacation(
            String employeeId,
            String dateFrom,
            String dateTo,
            String status);

    void removeVacation(String vacationId, String employeeUniqueId);

    void approveVacation(String vacationId, String employeeUniqueId);

    void rejectVacation(String vacationId, String employeeUniqueId);

    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO getEmployee(String id);

    Employee findEmployee(String employeeId);

    VacationDTO findVacation(String employeeId, String vacationId);

    List<VacationDTO> getVacations(String employeeId);
}
