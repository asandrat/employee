package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;

import java.util.Collection;

public interface EmployeeRepositoryDB {
    Collection<Employee> findAllEmployees();

    void addEmployee(Employee employee);

    Employee findEmployeeById(long id);

     void deleteEmployeeById(long id);

}
