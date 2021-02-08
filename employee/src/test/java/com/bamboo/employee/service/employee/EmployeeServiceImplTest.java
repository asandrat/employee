package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void add() {
        EmployeeDTO employeeDTO = employeeService.addEmployee("Eva",
                "Longoria");
        Assertions.assertEquals("Eva", employeeDTO.getName());
        Assertions.assertEquals("Longoria", employeeDTO.getSurname());
    }

    @Test
    void remove() {
        EmployeeDTO employeeDTO = employeeService.addEmployee("Eva",
                "Longoria");
        int oldSize = employeeService.findAll().size();
        employeeService.removeEmployee(employeeDTO.getId());
        Assertions.assertEquals(oldSize - 1, employeeService.findAll().size());
    }

    @Test
    void findAll() {
        Collection<EmployeeDTO> employees = employeeService.findAll();
        Set<String> namesSet =
                employees.stream().map(EmployeeDTO::getName).collect(Collectors.toSet());
        Assertions.assertNotNull(employees);
        Assertions.assertTrue(namesSet.containsAll(Arrays.asList(
                "Anica", "Branka", "Tereza", "Natasa")));
    }

    @Test
    void findAllVacations() {
        Collection<VacationDTO> vacations =
                employeeService.findAllVacationsOfEmployee("2");
        Set<String> dateFromSet =
                vacations.stream().map(VacationDTO::getDateFrom).collect(Collectors.toSet());
        Assertions.assertNotNull(vacations);
        Assertions.assertTrue(dateFromSet.containsAll(Arrays.asList(
                "2021-04-01", "2021-05-01")));
    }

}