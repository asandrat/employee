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
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddEmployeeCommandTest {
    @Mock
    EmployeeService employeeService;

    @Captor
    ArgumentCaptor<String> argumentCaptorString;

    @InjectMocks
    private AddEmployeeCommand addEmployeeCommand;

    @Test
    void getActionShouldReturnEmployeeAddition() {
        Assertions.assertEquals("employee_addition",
                addEmployeeCommand.getAction());
    }

    @Test
    public void executeShouldAddEmployeeToService() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "Zdravko");
        data.put("surname", "Colic");
        addEmployeeCommand.execute(data);
        //verify(employeeService).addEmployee(argumentCaptorString.capture(),
          //      argumentCaptorString.capture());
        List<String> captured = argumentCaptorString.getAllValues();
        Assertions.assertEquals("Zdravko",captured.get(0));
        Assertions.assertEquals("Colic",captured.get(1));
    }

}
