package com.bamboo.employee.service.employee;

import com.bamboo.employee.exceptions.InvalidStateTransitionException;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;

import java.util.Collection;

public interface EmployeeService {

    Collection<Employee> findAll();

    Employee addEmployee(Employee employee);

    Employee getEmployee(int id);

    Employee removeEmployee(int id);

    Vacation addVacationToEmployee(Vacation vacation);

    Vacation getVacationFromEmployee(VacationId vacationId);

    Vacation removeVacationFromEmployee(VacationId id);

    Vacation updateVacationForEmployee(VacationId id, VacationStatus status);

}
