package com.bamboo.employee.repository.employee;

import com.bamboo.employee.entities.Employee;
import com.bamboo.employee.repository.FileReaderAndWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    FileReaderAndWriter fileReaderAndWriter;

    @Value("${spring.file.name.employees}")
    private String fileNameEmployees;

    private Map<String, Employee> employeeMap;

    @PostConstruct
    public void init() {
        System.out.println("init: " + fileNameEmployees);
        employeeMap = findAll();
    }

    @Override
    public Map<String, Employee> findAll() {
        //read from file
        return fileReaderAndWriter.findAllEmployees();
    }

    @Override
    public void saveAll(Map<String, Employee> employeeMap) {
        //write to file
        fileReaderAndWriter.saveAllEmployees(employeeMap);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
        fileReaderAndWriter.saveAllEmployees(employeeMap);
    }


    @Override
    public boolean removeEmployee(String id) {
        if(employeeMap.remove(id) == null){
            return false;
        }
        fileReaderAndWriter.saveAllEmployees(employeeMap);
        return true;
    }
    @Override
    public Employee findEmployee(String id) {
        return employeeMap.get(id);
    }

}
