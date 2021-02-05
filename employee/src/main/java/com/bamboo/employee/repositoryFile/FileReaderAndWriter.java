package com.bamboo.employee.repositoryFile;

import com.bamboo.employee.entitiesFile.EmployeeFile;
import com.bamboo.employee.entitiesFile.VacationFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface FileReaderAndWriter {

    Map<String, EmployeeFile> findAllEmployees();

    void saveAllEmployees(Map<String, EmployeeFile> employeeMap);

    Map<String, VacationFile> findAllVacations();

    void saveAllVacations(Map<String, VacationFile> map);

    boolean isFileEmpty(File file) throws IOException;
}
