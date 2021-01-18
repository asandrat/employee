package com.bamboo.employee.service.command;

import com.bamboo.employee.service.employee.EmployeeService;
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
    EmployeeService employeeService;

    @Captor
    ArgumentCaptor<String> argumentCaptorString;

    @InjectMocks
    private RemoveEmployeeCommand removeEmployeeCommand;

    @Test
    void getActionShouldReturnEmployeeRemoval() {
        Assertions.assertEquals("employee_removal",
                removeEmployeeCommand.getAction());
    }

    @Test
    public void executeShouldRemoveEmployeeFromService() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "employeeId456");
        removeEmployeeCommand.execute(data);
        verify(employeeService).removeEmployee(argumentCaptorString.capture());
        Assertions.assertEquals("employeeId456",
                argumentCaptorString.getValue());
    }

}