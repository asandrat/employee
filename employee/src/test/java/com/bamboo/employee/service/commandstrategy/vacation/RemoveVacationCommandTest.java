package com.bamboo.employee.service.commandstrategy.vacation;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoveVacationCommandTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private RemoveVacationCommand command;
    private Map<String, String> args;
    private VacationId id;
    private Vacation v;

    @Captor
    private ArgumentCaptor<VacationId> captor;

    @BeforeEach
    void init() {
        args = new HashMap<>();
        args.put("uniqueId", "1");
        args.put("employeeUniqueId", "1");

        id = new VacationId(1, 1);
        v = new Vacation(id);
    }


    @Test
    void removeVacationComandShouldDelegateToService() {
        when(service.removeVacationFromEmployee(id)).thenReturn(v);
        Assertions.assertEquals(v, service.removeVacationFromEmployee(id));
    }

    @Test
    void removeVacationShouldPassVacationIdToService() {
        command.execute(args);
        verify(service).removeVacationFromEmployee(captor.capture());
        Assertions.assertEquals(id, captor.getValue());
    }
}
