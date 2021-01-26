package com.bamboo.employee.service.employee;

import com.bamboo.employee.entities.Employee;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.modelMapper = new ModelMapper();
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO addEmployee(String name, String surname) {
        final String id = UUID.randomUUID().toString();
        System.out.println("service: " + id);
        Employee employee = new Employee(id, name, surname);
        employeeRepository.addEmployee(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public Optional<Employee> removeEmployee(String id) {
        return employeeRepository.removeEmployee(id);
    }

    @Override
    public void saveAll(Map<String, Employee> map) {
        employeeRepository.saveAll(map);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        List<Employee> list =
                new ArrayList<>(employeeRepository.findAll().values());
        Type listType = new TypeToken<List<EmployeeDTO>>() {}.getType();
        return modelMapper.map(list, listType);
    }
}
