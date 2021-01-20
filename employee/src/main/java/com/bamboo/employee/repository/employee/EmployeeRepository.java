package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;

import java.util.Collection;

public interface EmployeeRepository {

    Collection<Employee> findAll();

    Employee read(int employeeId);

    boolean create(Employee employee);

    void saveAll(Collection<Employee> employees);

    void addVacationToEmployee(Vacation vacation);

    Employee delete(Integer id);

    Vacation deleteVacation(VacationId id);

    void update(VacationId vacationId, VacationStatus status);
}
