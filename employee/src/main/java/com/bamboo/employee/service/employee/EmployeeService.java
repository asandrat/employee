package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;

import java.util.Collection;

public interface EmployeeService {

    Collection<Employee> findAll();

    boolean addEmployee(Employee employee);

    Employee getEmployee(int id);

    Employee removeEmployee(int id);

    void addVacationToEmployee(Vacation vacation);

    Vacation getVacationFromEmployee(VacationId vacationId);

    Vacation removeVacationFromEmployee(VacationId id);

    boolean approveVacationForEmployee(VacationId vacationId);

    boolean rejectVacationForEmployee(VacationId vacationId);
}
