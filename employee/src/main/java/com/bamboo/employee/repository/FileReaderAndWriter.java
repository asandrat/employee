package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface FileReaderAndWriter {

    Map<String, Employee> findAllEmployees();

    void saveAllEmployees(Map<String, Employee> employeeMap);

    Map<String, Vacation> findAllVacations();

    void saveAllVacations(Map<String, Vacation> map);

    boolean isFileEmpty(File file) throws IOException;
}
