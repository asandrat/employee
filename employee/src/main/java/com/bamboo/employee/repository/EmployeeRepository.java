package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;

import java.util.Collection;

public interface EmployeeRepository {

    Collection<Employee> findAll();
    void create(Employee employee);
    void create(Vacation vacation);
    Employee read(int employeeId);
    Vacation read(VacationId vacationId);

    void saveAll(Collection<Employee> employees);

    void delete(Integer id);
}
