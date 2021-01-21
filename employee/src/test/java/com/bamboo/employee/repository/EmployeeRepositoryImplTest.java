package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryImplTest {

    private EmployeeRepositoryImpl employeeRepository;

    @Mock
    private ObjectMapper objectMapper;

    private Map<String, Employee> employeeList;

    @BeforeEach
    void setUp() {
        employeeRepository = new EmployeeRepositoryImpl(
                "",
                objectMapper);
        employeeList = new HashMap<>();
    }

    @Test
    void Should_ReturnAllEmployees() throws IOException {
        Employee employee = new Employee(
                "abc123",
                "Petar",
                "Peric"
        );
        employeeList.put(employee.getUniqueId(), employee);

        when(objectMapper.readValue(any(File.class), any(TypeReference.class)))
                .thenReturn(employeeList);

        Map<String, Employee> employeeMap = employeeRepository.findAll();
        assertThat(employeeMap.size()).isEqualTo(1);
        assertThat(employeeMap.get("abc123")).isEqualTo(employee);
    }

    @Test
    void Should_SaveAllEmployees() throws IOException {
        employeeRepository.saveAll(employeeList);
        verify(objectMapper).writeValue(any(File.class), any(Map.class));
    }
}
