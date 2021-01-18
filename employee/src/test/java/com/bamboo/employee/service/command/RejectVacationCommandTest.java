package com.bamboo.employee.service.command;

import com.bamboo.employee.service.vacation.VacationService;
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
class RejectVacationCommandTest {
    @Mock
    VacationService vacationService;

    @Captor
    ArgumentCaptor<String> argumentCaptorString;

    @InjectMocks
    RejectVacationCommand rejectVacationCommand;

    @Test
    void getActionShouldReturnVacationRejection() {
        Assertions.assertEquals("vacation_rejection",
                rejectVacationCommand.getAction());
    }

    @Test
    public void executeShouldRejectVacation() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "vacationId");
        data.put("status", "rejected");
        rejectVacationCommand.execute(data);
        verify(vacationService).rejectVacation(argumentCaptorString.capture());
        List<String> captured = argumentCaptorString.getAllValues();
        Assertions.assertEquals(captured.get(0),"vacationId");

    }

}