package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.repository.EmployeeRepository;
import com.bamboo.employee.repository.EntityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    EntityRepository<Employee> dao;

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public void setDao(EntityRepository<Employee> daoToSet) {
        dao = daoToSet;
        dao.setEntityType(Employee.class);
    }

    @Override
    @Transactional
    public EmployeeDTO addEmployee(String name, String surname) {
        Employee employee = new Employee(name, surname);
        Employee dbEmployee = employeeRepository.save(employee);
        return modelMapper.map(dbEmployee, EmployeeDTO.class);
    }

    @Override
    @Transactional
    public void removeEmployee(int employeeId) {
        checkIfEmployeeExists(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    @Override
    @Transactional
    public List<EmployeeDTO> findAllEmployees() {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.findAll());
        Type listType = new TypeToken<List<EmployeeDTO>>(){}.getType();
        return modelMapper.map(employeeList,listType);
    }

    @Override
    @Transactional
    public EmployeeDTO getEmployee(int employeeId) {
        Employee employee = checkIfEmployeeExists(employeeId);
        return modelMapper.map(
                employee,
                EmployeeDTO.class
        );
    }

    public Employee checkIfEmployeeExists(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id: " + employeeId
            );
        }
        return employee;
    }
}
