package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;

public interface EmployeeService {
    void addEmployee(final Employee employee);
    void addVacation(final Vacation vacation);
    Employee getEmployee(final int id);
    Vacation getVacation(final VacationId id);
}
