package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;

import java.util.Collection;

public interface EmployeeRepository {

    Collection<Employee> findAll();

    Employee read(int employeeId);

    void create(Employee employee);

    void saveAll(Collection<Employee> employees);

    void addVacationToEmployee(Vacation vacation);

    void delete(Integer id);

    void removeEmployeesVacation(VacationId id);
}
