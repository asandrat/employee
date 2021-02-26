package com.bamboo.employee.repository.employee;

import com.bamboo.employee.entity.FavoriteVacationEntity;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.entity.VacationEntity;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {


    @NotNull
    Collection<EmployeeEntity> findAll();

    Optional<EmployeeEntity> read(int employeeId);

    EmployeeEntity create(EmployeeEntity employeeEntity);

    Optional<EmployeeEntity> delete(int id);

    Collection<VacationEntity> findAllEmployeesVacations(int employeeId);

    Optional<VacationEntity> findEmployeesVacationById(int employeeId, int vacationId);

    List<EmployeeEntity> findFirstNEmployeesByTimestamp(int maxNumberOfEmployees, Timestamp timestamp);

    void createEmployeesFavoriteVacation(FavoriteVacationEntity favoriteVacationEntity);

    void deleteEmployeesFavoriteVacations(int uniqueId);
}
