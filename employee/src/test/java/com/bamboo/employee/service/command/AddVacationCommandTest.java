package com.bamboo.employee.service.command;

import com.bamboo.employee.service.vacation.VacationService;
import com.bamboo.employee.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AddVacationCommandTest {
    @Mock
    VacationService vacationService;

    @Captor
    ArgumentCaptor<String> argumentCaptorString;

    @InjectMocks
    private AddVacationCommand addVacationCommand;

    @Test
    void getActionShouldReturnVacationAddition() {
        Assertions.assertEquals("vacation_addition",
                addVacationCommand.getAction());
    }

    @Test
    public void executeShouldAddVacationToService() {
        Map<String, String> data = new HashMap<>();
        data.put("employeeId", "employeeId123");
        data.put("dateFrom", "2021-03-01");
        data.put("dateTo", "2021-03-05");
        data.put("status", "submitted");
        addVacationCommand.execute(data);
        verify(vacationService).addVacation(
                argumentCaptorString.capture(), argumentCaptorString.capture(),
                argumentCaptorString.capture(), argumentCaptorString.capture());
        List<String> captured = argumentCaptorString.getAllValues();
        Assertions.assertEquals(captured.get(0),"employeeId123");
        Assertions.assertEquals(captured.get(1),"2021-03-01");
        Assertions.assertEquals(captured.get(2),"2021-03-05");
        Assertions.assertEquals(captured.get(3),"submitted");
    }

}