package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;

import java.util.Collection;

public interface EmployeeRepository {

    Collection<Employee> findAll();

    void saveAll(Collection<Employee> employees);
}
