package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;

public interface EmployeeService {

    void addEmployee(Employee employee);

    Employee getEmployee(int id);

    void removeEmployee(Integer id);
}
