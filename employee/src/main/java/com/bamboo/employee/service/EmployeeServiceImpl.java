package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Override
    public void addEmployee(Employee employee) {
        repository.create(employee);
    }

    @Override
    public void addVacation(Vacation vacation) {
        repository.create(vacation);
    }

    @Override
    public Employee getEmployee(int id) {
        return repository.read(id);
    }

    @Override
    public Vacation getVacation(VacationId id) {
        return repository.read(id);
    }
}
