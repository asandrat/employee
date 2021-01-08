package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;

import java.util.Collection;

public interface EmployeeRepository {

    Collection<Employee> findAll();
    void create(final Employee employee);
    void create(final Vacation vacation);
    Employee read(final int employeeId);
    Vacation read(final VacationId vacationId);

    void saveAll(final Collection<Employee> employees);
}
