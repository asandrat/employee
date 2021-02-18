package com.bamboo.employee.service;

import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.model.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO addEmployee(String name, String surname);

    void removeEmployee(int employeeId);

    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO getEmployee(int id);

    Employee checkIfEmployeeExists(int employeeId);

    void calculateFavoriteMonthsForEmployees();

    void saveFavoriteMonth(Employee e, List<Integer> month);
}
