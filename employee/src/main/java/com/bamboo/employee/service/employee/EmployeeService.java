package com.bamboo.employee.service.employee;

import com.bamboo.employee.entities.Employee;
import com.bamboo.employee.model.EmployeeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO addEmployee(String name, String surname);

    Optional<Employee> removeEmployee(String id);

    void saveAll(Map<String, Employee> map);

    List<EmployeeDTO> findAll();
}
