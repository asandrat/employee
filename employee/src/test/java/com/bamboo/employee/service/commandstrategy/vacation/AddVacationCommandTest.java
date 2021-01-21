package com.bamboo.employee.service.commandstrategy.vacation;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddVacationCommandTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private AddVacationCommand command;

    @Captor
    private ArgumentCaptor<Vacation> vacataionCaptor;

    private Map<String, String> args;
    private Vacation v;

    @BeforeEach
    void init() {
        args = new HashMap<>();
        args.put("employeeUniqueId", "1");
        args.put("uniqueId", "1");
        args.put("from", "2021-01-01");
        args.put("to", "2021-02-01");
        args.put("status", "SUBMITTED");

        v = new Vacation(
                1,
                1,
                LocalDate.parse("2021-01-01"),
                LocalDate.parse("2021-02-01"),
                null,
                VacationStatus.SUBMITTED);
    }

    @Test
    void addVacationShouldDelegateToService() {
        doNothing().when(service).addVacationToEmployee(v);
        Assertions.assertTrue((boolean) command.execute(args));
    }


    @Test
    void shouldMakeValidVacationInstance() {
        command.execute(args);
        verify(service).addVacationToEmployee(vacataionCaptor.capture());
        Vacation v1 = vacataionCaptor.getValue();
        Assertions.assertEquals(v, v1);
        Assertions.assertEquals(v.getDuration(), v1.getDuration());
        Assertions.assertEquals(v.getFrom(), v1.getFrom());
        Assertions.assertEquals(v.getTo(), v1.getTo());
        Assertions.assertEquals(v.getId(), v1.getId());
        Assertions.assertEquals(v.getStatus(), v1.getStatus());
    }
}
