package com.bamboo.employee.controller;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.ServerResponse;
import com.bamboo.employee.service.EmployeeService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDTO employee;

    @BeforeEach
    void setUp() {
        employee = new EmployeeDTO();
        employee.setId("1");
        employee.setName("John");
        employee.setSurname("Wick");
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {

        when(employeeService.getEmployee(anyInt())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(employee.getSurname()))
                .andExpect(status().isOk());
    }

    @Test
    public void saveEmployeeTest() throws Exception {

        when(employeeService.addEmployee(anyString(), anyString())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .content(new ObjectMapper().writeValueAsString(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(employee.getSurname()));
    }

    @Test
    public void getAllEmployeesTest() throws Exception {

        List<EmployeeDTO> employees = Collections.singletonList(employee);

        when(employeeService.findAllEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname").value(employees.get(0).getSurname()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {

        ServerResponse serverResponse = new ServerResponse(
                "Employee with id: 12 is successfully deleted"
        );
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(serverResponse.getMessage()))
                .andExpect(status().isOk());
    }
}
