package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.EmployeesFavouriteMonth;
import com.bamboo.employee.entitiesDB.Vacation;

import java.util.Collection;
import java.util.List;

public interface EmployeeRepositoryDB {
    Collection<Employee> findAllEmployees();

    void addEmployee(Employee employee);

    Employee findEmployeeById(long id);

     void deleteEmployee(Employee employee);

    Collection<Vacation> findAllVacationsOfEmployee(long id);

    List<Employee> findFirstN(int n);

    void saveFavoriteMonth(EmployeesFavouriteMonth employeesFavoriteMonth);

}
