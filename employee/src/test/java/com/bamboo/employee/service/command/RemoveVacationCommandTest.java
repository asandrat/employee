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
import java.util.Map;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RemoveVacationCommandTest {
    @Mock
    VacationService vacationService;

    @Captor
    ArgumentCaptor<String> argumentCaptorString;

    @InjectMocks
    private RemoveVacationCommand removeVacationCommand;

    @Test
    void getActionShouldReturnVacationRemoval() {
        Assertions.assertEquals("vacation_removal",
                removeVacationCommand.getAction());
    }

    @Test
    public void executeShouldRemoveVacationFromService() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "vacationId789");
        removeVacationCommand.execute(data);
        verify(vacationService).removeVacation(argumentCaptorString.capture());
        Assertions.assertEquals("vacationId789",
                argumentCaptorString.getValue());
    }

}