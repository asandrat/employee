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
class RemoveVacationCommandTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private RemoveVacationCommand removeVacationCommand;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void Should_DelegateRemoveEmployeeToService() {
        Map<String, String> userData = new HashMap<>();
        String vacationId = "AYU77-9987JJ-OPO00";
        String employeeId = "BBU77-9987JI-OP990";
        userData.put("uniqueId", vacationId);
        userData.put("employeeUniqueId", employeeId);

        removeVacationCommand.execute(userData);
        verify(employeeService).removeVacation(
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture()
        );
        List<String> capturedArguments = stringArgumentCaptor.getAllValues();
        assertThat(capturedArguments.get(0)).isEqualTo(vacationId);
        assertThat(capturedArguments.get(1)).isEqualTo(employeeId);

    }

    @Test
    public void Should_ReturnRemoveVacationAction() {
        Assertions.assertEquals(
                "vacation_removal",
                removeVacationCommand.getAction()
        );
    }

}
