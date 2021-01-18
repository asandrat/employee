package com.bamboo.employee.service.command;

import com.bamboo.employee.service.EmployeeService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddVacationCommandTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private AddVacationCommand addVacationCommand;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void Should_DelegateAddVacationToService() {
        Map<String, String> userData = new HashMap<>();
        userData.put("employeeUniqueId", "AAA88-BBB99-89YYY");
        userData.put("dateFrom", "15/02/2021");
        userData.put("dateTo", "19/02/2021");
        userData.put("status", "approved");

        addVacationCommand.execute(userData);
        verify(employeeService).addVacation(
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture()
        );

        List<String> capturedValues = stringArgumentCaptor.getAllValues();
        assertThat(capturedValues.get(0)).isEqualTo("AAA88-BBB99-89YYY");
        assertThat(capturedValues.get(1)).isEqualTo("15/02/2021");
        assertThat(capturedValues.get(2)).isEqualTo("19/02/2021");
        assertThat(capturedValues.get(3)).isEqualTo("approved");
    }

    @Test
    public void Should_ReturnAddVacationAction() {
        Assertions.assertEquals(
                "vacation_addition",
                addVacationCommand.getAction());
    }

}
