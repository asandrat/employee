package com.bamboo.employee.service.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesFile.EmployeeFile;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.repositoryDB.employee.EmployeeRepositoryDB;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepositoryDB employeeRepositoryDB;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepositoryDB employeeRepositoryDB) {
        this.modelMapper = new ModelMapper();
        this.employeeRepositoryDB = employeeRepositoryDB;
    }

    @Override
    @Transactional
    public EmployeeDTO addEmployee(String name, String surname) {
        Employee employeeDB = new Employee(name, surname);
        employeeRepositoryDB.addEmployee(employeeDB);
        return modelMapper.map(employeeDB, EmployeeDTO.class);
    }

    @Override
    @Transactional
    public void removeEmployee(String id) {
        long longId = Long.parseLong(id);
        System.out.println("BRISANJE "+id+" SERVIS");
        Employee employee = employeeRepositoryDB.findEmployeeById(longId);
        if (employee == null) {
            System.out.println("ID NIJE NADJENN SERVIS");
            throw new IllegalArgumentException(
                    "Employee with id " + id + " not found.");
        }
        employeeRepositoryDB.deleteEmployeeById(longId);
    }

    @Override
    public void saveAll(Map<String, EmployeeFile> map) {
        //employeeRepository.saveAll(map);
    }

    @Override
    @Transactional
    public Collection<EmployeeDTO> findAll() {
        List<Employee> list =
                new ArrayList<>(employeeRepositoryDB.findAllEmployees());
        Type listType = new TypeToken<List<EmployeeDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }
}
