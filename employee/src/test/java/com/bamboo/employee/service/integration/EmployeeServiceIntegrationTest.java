package com.bamboo.employee.service.integration;

import com.bamboo.employee.custom.exception.ApplicationException;
import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void addEmployeeTest() {
        EmployeeDTO employeeDTO = employeeService.addEmployee(
                "Rachel",
                "Morry"
        );
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getName()).isEqualTo("Rachel");
        assertThat(employeeDTO.getSurname()).isEqualTo("Morry");
        assertThat(employeeDTO.getId()).isNotNull();
    }

    @Test
    void removeEmployeeTest() {
        EmployeeDTO employeeDTO = employeeService.addEmployee(
                "Thomas",
                "Sanchez"
        );
        employeeService.removeEmployee(employeeDTO.getId());
    }

    @Test
    void findAllEmployeesTest() {
        employeeService.addEmployee("Richard", "Acton");
        employeeService.addEmployee("Thomas", "Alby");

        List<EmployeeDTO> list = employeeService.findAllEmployees();
        assertThat(list).isNotNull();
        assertThat(list.get(0).getName()).isEqualTo("Richard");
        assertThat(list.get(1).getName()).isEqualTo("Thomas");
        assertThat(list.get(0).getSurname()).isEqualTo("Acton");
        assertThat(list.get(1).getSurname()).isEqualTo("Alby");
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void getEmployeeTest() {
        EmployeeDTO employeeDTO = employeeService.addEmployee(
                "Monica",
                "Geller"
        );
        EmployeeDTO returnedEmployee = employeeService.getEmployee(
                employeeDTO.getId()
        );
        assertThat(returnedEmployee).isNotNull();
        assertThat(returnedEmployee.getName()).isEqualTo("Monica");
        assertThat(returnedEmployee.getSurname()).isEqualTo("Geller");
    }

    @Test
    void getEmployeeWhenEmployeeIsNotFoundTest() {
        try {
            employeeService.getEmployee(5);
        } catch (ApplicationException e) {
            assertThat(e)
                    .isInstanceOf(EmployeeNotFoundException.class)
                    .hasMessage("Could not find employee with id: 5");
        }
    }

}
