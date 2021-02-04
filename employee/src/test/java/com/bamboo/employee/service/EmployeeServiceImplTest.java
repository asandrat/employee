package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private final EmployeeDTO employeeDTO = new EmployeeDTO();
    private final Employee employee = new Employee(
            "Rachel", "Thomas"
    );

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    ArgumentCaptor<Employee> employeeArgumentCaptor;

    @BeforeEach
    void setUp() {
        employeeDTO.setId("1");
        employeeDTO.setName("Rachel");
        employeeDTO.setSurname("Thomas");
    }

    @Test
    void addEmployeeTest() {

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(any(), any())).thenReturn(employeeDTO);
        EmployeeDTO employeeDTO = employeeService.addEmployee(
                "Monica",
                "Geller"
        );

        verify(employeeRepository).save(employeeArgumentCaptor.capture());

        assertThat(employeeDTO).isNotNull();
        assertThat(employeeArgumentCaptor.getValue().getName()).isEqualTo(
                "Monica"
        );
    }

    @Test
    void removeEmployeeTest() {

        doNothing().when(employeeRepository).deleteById(2);

        when(employeeRepository.findById(anyInt())).thenReturn(employee);

        employeeService.removeEmployee(2);

        verify(employeeRepository).deleteById(integerArgumentCaptor.capture());

        assertThat(integerArgumentCaptor.getValue()).isEqualTo(2);
    }

    @Test
    void getEmployeeTest() {

        when(modelMapper.map(any(), any())).thenReturn(employeeDTO);

        when(employeeRepository.findById(anyInt())).thenReturn(employee);

        EmployeeDTO employeeDTO = employeeService.getEmployee(2);

        verify(employeeRepository).findById(integerArgumentCaptor.capture());

        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getName()).isEqualTo("Rachel");
        assertThat(integerArgumentCaptor.getValue()).isEqualTo(2);
    }

    @Test
    void checkIfEmployeeExistsTest() {

        when(employeeRepository.findById(anyInt())).thenReturn(employee);

        Employee employee = employeeService.checkIfEmployeeExists(3);

        verify(employeeRepository).findById(integerArgumentCaptor.capture());

        assertThat(employee).isNotNull();
        assertThat(integerArgumentCaptor.getValue()).isEqualTo(3);
    }

    @Test
    void checkIfEmployeeExistsThrowsExceptionTest() {

        when(employeeRepository.findById(anyInt())).thenReturn(null);

        Exception exception = assertThrows(
                EmployeeNotFoundException.class,
                () ->
                        employeeService.checkIfEmployeeExists(2)
        );

        String expectedMessage = "Could not find employee with id: 2";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}
