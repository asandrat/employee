package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Map<Integer, Employee> findAll(int numberOfObjectsInFile, String fileName) {
        return employeeRepository.findAll(numberOfObjectsInFile, fileName);
    }

    @Override
    public int saveAll(Map<Integer, Employee> map, String fileName) {
        //write to file
        employeeRepository.saveAll(map, fileName);
        return map.size();
    }

    @Override
    public void addEmployee(int id, String name, String surname) {

    }

    @Override
    public void removeEmployee(int id) {

    }

    @Override
    public void addVacation(int id, int employeeId, String dateFrom,
                            String dateTo, String status) {

    }

    @Override
    public void removeVacation(int id, int employeeId) {

    }

    @Override
    public void approveVacation(int id, int employeeId, String status) {

    }

    @Override
    public void rejectVacation(int id, int employeeId, String status) {

    }


}
