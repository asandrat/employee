package com.bamboo.employee.service.employee;

import com.bamboo.employee.entitiesFile.EmployeeFile;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;

import java.util.Collection;
import java.util.Map;

public interface EmployeeService {
    EmployeeDTO addEmployee(String name, String surname);

    void removeEmployee(String id);

    void saveAll(Map<String, EmployeeFile> map);

    Collection<EmployeeDTO> findAll();

    Collection<VacationDTO> findAllVacationsOfEmployee(String id);

}
