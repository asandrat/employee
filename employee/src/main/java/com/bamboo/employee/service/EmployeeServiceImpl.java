package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.mapstruct.EmployeeMapper;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.repository.EmployeeRepository;
import com.bamboo.employee.repository.EntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EntityRepository<Employee> dao;

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;

    @Autowired
    public void setDao(EntityRepository<Employee> daoToSet) {
        dao = daoToSet;
        dao.setEntityType(Employee.class);
    }

    @Override
    @Transactional
    public EmployeeDTO addEmployee(String name, String surname) {
        Employee employee = new Employee(name, surname);
        employee.setRegisteredAt(LocalDateTime.now());
        Employee dbEmployee = employeeRepository.save(employee);
        return mapper.employeeToEmployeeDTO(dbEmployee);
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
        return mapper.map(employeeList);
    }

    @Override
    @Transactional
    public EmployeeDTO getEmployee(int employeeId) {
        Employee employee = checkIfEmployeeExists(employeeId);
        return mapper.employeeToEmployeeDTO(employee);
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
