package com.bamboo.employee.repository;

import com.bamboo.employee.entity.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee employee);

    void deleteById(int theId);

}
