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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoveEmployeeCommandTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private RemoveEmployeeCommand command;
    private Map<String, String> args;

    @Captor
    private ArgumentCaptor<Integer> captor;

    @BeforeEach
    void init() {
        args = new HashMap<>();
        args.put("uniqueId", "1");
    }


    @Test
    void removeEmployeeComandShouldDelegateToService() {
        Employee e = new Employee(1, "Petar", "Petrovski");
        when(service.removeEmployee(1)).thenReturn(e);

        Assertions.assertEquals(e, command.execute(args));
    }

    @Test
    void removeEmployeeShouldPassIntegerToService() {
        Employee e = new Employee(1, "Petar", "Petrovski");

        command.execute(args);
        verify(service).removeEmployee(captor.capture());
        Assertions.assertEquals(1, captor.getValue());
    }
}
