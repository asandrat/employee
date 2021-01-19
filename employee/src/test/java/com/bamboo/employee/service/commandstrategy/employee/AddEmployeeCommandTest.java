package com.bamboo.employee.service.commandstrategy.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.employee.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddEmployeeCommandTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private AddEmployeeCommand command;
    private Map<String, String> args;

    @Captor
    private ArgumentCaptor<Employee> captor;

    @BeforeEach
    void init() {
        args = new HashMap<>();
        args.put("uniqueId", "1");
        args.put("name", "petar");
        args.put("surname", "petarson");
    }

    @Test
    void addCommandShouldDelegateToService() {
        when(service.addEmployee(any(Employee.class))).thenReturn(true);
        Assertions.assertTrue((boolean)command.execute(args));
        verify(service).addEmployee(any(Employee.class));
    }

    @Test
    void addCommandShouldCreateAppropriateEmployee() {
        Employee e = new Employee(1, "petar", "petarson");
        command.execute(args);
        verify(service).addEmployee(captor.capture());
        Employee e1 = captor.getValue();
        Assertions.assertEquals(e, e1);
        Assertions.assertEquals(e.getName(), e1.getName());
        Assertions.assertEquals(e.getSurname(), e1.getSurname());
        assertEquals(e1.getVacations().size(), 0);
    }
}
