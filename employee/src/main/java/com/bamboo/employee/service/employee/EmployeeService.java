package com.bamboo.employee.service.employee;

import com.bamboo.employee.entities.Employee;
import com.bamboo.employee.model.EmployeeDTO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeDTO addEmployee(String name, String surname);

    void removeEmployee(String id);

    void saveAll(Map<String, Employee> map);

    List<EmployeeDTO> findAll();
}
