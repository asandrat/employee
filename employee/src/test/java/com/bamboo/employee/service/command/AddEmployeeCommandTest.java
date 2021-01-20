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
class AddEmployeeCommandTest {

    @Mock
    private EmployeeService employeeService;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @InjectMocks
    private AddEmployeeCommand addEmployeeCommand;

    @Test
    public void Should_DelegateAddEmployeeToService() {
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "Milena");
        userData.put("surname", "Milic");
        addEmployeeCommand.execute(userData);
        verify(employeeService).addEmployee(
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture()
        );
        List<String> capturedValues = stringArgumentCaptor.getAllValues();
        assertThat(capturedValues.get(0)).isEqualTo("Milena");
        assertThat(capturedValues.get(1)).isEqualTo("Milic");
    }

    @Test
    public void Should_ReturnAddEmployeeAction() {
        Assertions.assertEquals(
                "employee_addition",
                addEmployeeCommand.getAction());
    }
}
