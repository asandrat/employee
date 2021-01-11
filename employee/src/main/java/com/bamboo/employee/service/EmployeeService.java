package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;


public interface EmployeeService {
    void addEmployee(Employee employee);
    void addVacation(Vacation vacation);

    Employee getEmployee(int id);
    Vacation getVacation(VacationId id);

    void removeEmployee(Integer id);
}
