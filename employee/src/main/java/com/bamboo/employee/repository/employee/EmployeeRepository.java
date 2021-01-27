package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeRepository {


    @NotNull
    Collection<Employee> findAll();

    Optional<Employee> read(int employeeId);

    Employee create(Employee employee);

    void saveAll(Collection<Employee> employees);

    Vacation addVacationToEmployee(Vacation vacation);

    Optional<Employee> delete(int id);

    Vacation deleteVacation(VacationId id);

    void update(VacationId vacationId, VacationStatus status);
}
