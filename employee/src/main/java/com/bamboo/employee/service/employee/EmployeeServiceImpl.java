package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public void addEmployee(final Employee employee) {
        repository.create(employee);
    }

    @Override
    public Employee getEmployee(final int id) {
        return repository.read(id);
    }

    @Override
    public void removeEmployee(Integer id) {
        repository.delete(id);
    }

}
