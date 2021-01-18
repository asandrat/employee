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
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RemoveEmployeeCommandTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private RemoveEmployeeCommand removeEmployeeCommand;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void Should_DelegateRemoveEmployeeToService() {
        Map<String, String> userData = new HashMap<>();
        String employeeUniqueId = "AYU77-9987JJ-OPO00";
        userData.put("uniqueId", employeeUniqueId);

        removeEmployeeCommand.execute(userData);
        verify(employeeService).removeEmployee(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(employeeUniqueId);
    }

    @Test
    public void Should_ReturnRemoveEmployeeAction() {
        Assertions.assertEquals(
                "employee_removal",
                removeEmployeeCommand.getAction()
        );
    }

}
