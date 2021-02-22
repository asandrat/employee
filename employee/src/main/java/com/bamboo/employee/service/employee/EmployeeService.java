package com.bamboo.employee.service.employee;

import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.FavoriteVacation;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface EmployeeService {

    Collection<Employee> findAll();

    Employee addEmployee(Employee employee);

    Employee getEmployee(int id);

    Employee removeEmployee(int id);

    @Deprecated
    Vacation addVacationToEmployee(Vacation vacation);

    Vacation addVacationToEmployee(int employeeId, Vacation vacation);

    @Deprecated
    Vacation getVacationFromEmployee(VacationId vacationId);

    Vacation getVacationFromEmployee(int employeeId, int vacationId);

    @Deprecated
    Vacation removeVacationFromEmployee(VacationId id);

    void removeVacationFromEmployee(int employeeId, int vacationId);

    Vacation updateVacationForEmployee(VacationId id, VacationStatus status);

    void updateVacationForEmployee(int employeeId,
                                  int vacationId,
                                  VacationStatus status);

    Collection<Vacation> findAllEmployeesVacations(int employeeId);

    void createEmployeesFavoriteVacation(FavoriteVacation favoriteVacation);

    List<Employee> findFirstNEmployeesByTimestamp(
            int maxNumberOfEmployeesPerTask,
            Timestamp timestamp);

    void removeEmployeesFavoriteVacations(int uniqueId);
}
