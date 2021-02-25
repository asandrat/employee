package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @AfterEach//to track database state
    void printNames() {
        employeeService.findAll()
                .forEach(employee -> System.out.println(employee.getName()));
    }

    @BeforeEach
    void addRecordsToDB(){
        employeeService.addEmployee("Anica", "Dobra", "2010-09-01");
        employeeService.addEmployee("Branka", "Katic", "2010-09-01");
        employeeService.addEmployee("Tereza", "Kesofija", "2010-09-01");
        employeeService.addEmployee("Natasa", "Markovic", "2010-09-01");
    }

    @Test
    void add() {
        int oldSize = employeeService.findAll().size();
        EmployeeDTO employeeDTO = employeeService.addEmployee(
                "Eva", "Longoria", "2010-09-01");
        Assertions.assertEquals(oldSize + 1, employeeService.findAll().size());
        Assertions.assertEquals("Eva", employeeDTO.getName());
        Assertions.assertEquals("Longoria", employeeDTO.getSurname());
        employeeService.removeEmployee(employeeDTO.getId());
        Assertions.assertEquals(oldSize, employeeService.findAll().size());
    }

    @Test
    void remove() {
        int oldSize = employeeService.findAll().size();
        EmployeeDTO employeeDTO = employeeService.addEmployee(
                "Eva", "Longoria", "2010-09-01");
        Assertions.assertEquals(oldSize + 1, employeeService.findAll().size());
        employeeService.removeEmployee(employeeDTO.getId());
        Assertions.assertEquals(oldSize, employeeService.findAll().size());
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
                employeeService.findAllVacationsOfEmployee("15");
        Assertions.assertNotNull(vacations);
    }

}