package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;

import java.util.Collection;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public Collection<Employee> findAll() {
        //TODO: delegate to repository to find all employees
        return null;
    }

    @Override
    public void saveAll(Collection<Employee> employees) {
        //TODO: delegate to repository to save employees
    }
}
