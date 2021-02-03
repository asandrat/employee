package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.EmployeeEntity;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationEntity;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeRepository {


    @NotNull
    Collection<EmployeeEntity> findAll();

    Optional<EmployeeEntity> read(int employeeId);

    EmployeeEntity create(EmployeeEntity employeeEntity);

    void saveAll(Collection<Employee> employees);

    VacationEntity addVacationToEmployee(EmployeeEntity employeeEntity,
                                         VacationEntity vacationEntity);

    Optional<EmployeeEntity> delete(int id);

    Vacation deleteVacation(VacationId id);

    int deleteVacation(int employeeId, int vacationId);

    void update(VacationId vacationId, VacationStatus status);

    int update(int employeeId, int vacationId, VacationStatus status);

    Collection<VacationEntity> findAllEmployeesVacations(int employeeId);

    Optional<VacationEntity> findEmployeesVacationById(int employeeId, int vacationId);
}
