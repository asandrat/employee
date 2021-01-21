package com.bamboo.employee.service.commandstrategy.vacation;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VacationApprovalCommandTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private VacationApprovalCommand command;
    private Map<String, String> args;
    private VacationId id;

    @Captor
    private ArgumentCaptor<VacationId> captor;

    @BeforeEach
    void init() {
        args = new HashMap<>();
        args.put("uniqueId", "1");
        args.put("employeeUniqueId", "1");

        id = new VacationId(1, 1);
    }

    @Test
    void approveVacationComandShouldDelegateToService() {
        when(service.approveVacationForEmployee(id)).thenReturn(true);
        Assertions.assertTrue(service.approveVacationForEmployee(id));
    }

    @Test
    void approveVacationShouldPassVacationIdToService() {
        command.execute(args);
        verify(service).approveVacationForEmployee(captor.capture());
        Assertions.assertEquals(id, captor.getValue());
    }
}
