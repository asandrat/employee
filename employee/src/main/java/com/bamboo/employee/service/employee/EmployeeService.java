package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;

import java.util.Collection;

public interface EmployeeService {
    EmployeeDTO addEmployee(String name, String surname, String registrationDate);

    void removeEmployee(String id);

    Collection<EmployeeDTO> findAll();

    Collection<VacationDTO> findAllVacationsOfEmployee(String id);

    Collection<EmployeeDTO> findFirstN(int n);

    void saveFavouriteMonth(EmployeeDTO employeeDTO, int favouriteMonth);
}
