package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;


public interface EmployeeService {

    void addEmployee(Employee employee);

    Employee getEmployee(int id);

    void removeEmployee(Integer id);
}
