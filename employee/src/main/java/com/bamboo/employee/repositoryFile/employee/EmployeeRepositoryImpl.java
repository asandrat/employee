package com.bamboo.employee.repositoryFile.employee;

import com.bamboo.employee.entitiesFile.EmployeeFile;
import com.bamboo.employee.repositoryFile.FileReaderAndWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    FileReaderAndWriter fileReaderAndWriter;

    @Value("${spring.file.name.employees}")
    private String fileNameEmployees;

    private Map<String, EmployeeFile> employeeMap;

    @PostConstruct
    public void init() {
        System.out.println("init: " + fileNameEmployees);
        employeeMap = findAll();
    }

    @Override
    public Map<String, EmployeeFile> findAll() {
        return fileReaderAndWriter.findAllEmployees();
    }

    @Override
    public void saveAll(Map<String, EmployeeFile> employeeMap) {
        fileReaderAndWriter.saveAllEmployees(employeeMap);
    }

    @Override
    public void addEmployee(EmployeeFile employee) {
        employeeMap.put(employee.getId(), employee);
        fileReaderAndWriter.saveAllEmployees(employeeMap);
    }


    @Override
    public Optional<EmployeeFile> removeEmployee(String id) {
        Optional<EmployeeFile> employee = Optional.ofNullable(employeeMap.remove(id));

        fileReaderAndWriter.saveAllEmployees(employeeMap);
        return employee;
    }

    @Override
    public EmployeeFile findEmployee(String id) {
        return employeeMap.get(id);
    }

}
