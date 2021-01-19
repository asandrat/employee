package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    @Captor
    ArgumentCaptor<String> argumentCaptorString;

    @Captor
    ArgumentCaptor<Employee> argumentCaptorEmployee;

    @Test
    void addEmployeeToRepository() {
        employeeServiceImpl.addEmployee("Mihailo", "Petrovic");
        verify(employeeRepository).addEmployee(argumentCaptorEmployee.capture());

        Employee employee = argumentCaptorEmployee.getValue();
        Assertions.assertEquals("Mihailo", employee.getName());
        Assertions.assertEquals("Petrovic", employee.getSurname());
    }

    @Test
    void removeEmployeeFromRepository() {
        employeeServiceImpl.removeEmployee("123");
        verify(employeeRepository).removeEmployee(argumentCaptorString.capture());
        Assertions.assertEquals("123", argumentCaptorString.getValue());
    }


    @Test
    void saveAllEmployeesToRepository() {
        Map<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("123", new Employee("123", "Andjela", "Krizan"));
        employeeServiceImpl.saveAll(employeeMap);
        verify(employeeRepository).saveAll(employeeMap);
    }

    @Test
    void findAllEmployeesFromRepository() {
        employeeServiceImpl.findAll();
        verify(employeeRepository).findAll();
    }
}