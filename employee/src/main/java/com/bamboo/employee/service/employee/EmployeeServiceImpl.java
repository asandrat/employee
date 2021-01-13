package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(String name, String surname) {
        final String id = UUID.randomUUID().toString();
        System.out.println("service: "+id);
        Employee employee = new Employee(id, name, surname);
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void removeEmployee(String id) {
        employeeRepository.removeEmployee(id);
    }

    @Override
    public void saveAll(Map<String, Employee> map) {
        //write to file
        employeeRepository.saveAll(map);
    }

    @Override
    public Map<String, Employee> findAll() {
        return employeeRepository.findAll();
    }
}
