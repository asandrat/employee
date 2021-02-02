package com.bamboo.employee.repositoryFile.employee;

import com.bamboo.employee.entitiesFile.EmployeeFile;

import com.bamboo.employee.repositoryFile.FileReaderAndWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryImplTest {
    @Mock
    FileReaderAndWriter fileReaderAndWriter;

    @InjectMocks
    EmployeeRepositoryImpl employeeRepositoryImpl;

    @BeforeEach
    void setEmployeeRepositoryImpl() {
        employeeRepositoryImpl.init();
    }

    @Test
    void addEmployee() {
        EmployeeFile employee = new EmployeeFile("123", "Frank", "Sinatra");
        employeeRepositoryImpl.addEmployee(employee);
        Assertions.assertEquals("Frank",
                employeeRepositoryImpl.findEmployee("123").getName());
    }

    @Test
    void removeEmployee() {
        employeeRepositoryImpl.removeEmployee("123");
        Assertions.assertNull(employeeRepositoryImpl.findEmployee("123"));
    }

    @Test
    void findEmployee() {
        EmployeeFile employee = new EmployeeFile("456", "Dolly", "Parton");
        employeeRepositoryImpl.addEmployee(employee);
        Assertions.assertEquals("Parton",
                employeeRepositoryImpl.findEmployee("456").getSurname());
    }

}