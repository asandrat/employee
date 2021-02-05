package com.bamboo.employee.service.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.entitiesFile.EmployeeFile;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repositoryDB.employee.EmployeeRepositoryDB;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepositoryDB employeeRepositoryDB;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepositoryDB employeeRepositoryDB) {
        this.modelMapper = new ModelMapper();
        this.employeeRepositoryDB = employeeRepositoryDB;
    }

    @Override
    public EmployeeDTO addEmployee(String name, String surname) {
        Employee employeeDB = new Employee(name, surname);
        employeeRepositoryDB.addEmployee(employeeDB);
        return modelMapper.map(employeeDB, EmployeeDTO.class);
    }

    @Override
    public void removeEmployee(String id) {
        long longId = Long.parseLong(id);
        Employee employee = employeeRepositoryDB.findEmployeeById(longId);
        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee with id " + id + " not found.");
        }
        employeeRepositoryDB.deleteEmployee(employee);
    }

    @Override
    public void saveAll(Map<String, EmployeeFile> map) {
        //employeeRepository.saveAll(map);
    }

    @Override
    public Collection<EmployeeDTO> findAll() {
        List<Employee> list =
                new ArrayList<>(employeeRepositoryDB.findAllEmployees());
        Type listType = new TypeToken<List<EmployeeDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public Collection<VacationDTO> findAllVacationsOfEmployee(String id) {
        long longId = Long.parseLong(id);
        List<Vacation> list = new ArrayList<>(
                employeeRepositoryDB.findAllVacationsOfEmployee(longId));
        Type listType = new TypeToken<List<VacationDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

}
