package com.bamboo.employee.service.employee;


import com.bamboo.employee.exceptions.EmployeeNotFoundException;
import com.bamboo.employee.exceptions.VacationNotFoundException;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootTest
@Transactional // for rolling back after each test
@ActiveProfiles("test")
/*
some test work in isolation but fail when whole test suite is run. Currently
using BEFORE_EACH_TEST_METHOD which works fine but heavily slows down
execution time; todo is there a better solution ?
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmployeeServiceImplIntegrationTest {

    @Autowired
    private EmployeeService service;

    @Test
    void sanityCheck() {
        Assertions.assertNotNull(service);
    }

    @Test
    void addingEmployeeShouldIncreaseSize() {

        int numberOfPreloadedEmployees = service.findAll().size();

        Employee employee = new Employee();
        employee.setName("Test");
        employee.setSurname("Testovski");
        service.addEmployee(employee);

        Assertions.assertEquals(
                numberOfPreloadedEmployees + 1,
                service.findAll().size());
    }

    @Test
    void shouldMakeEmptyEmployeeTable() {
        Assertions.assertEquals(0, service.findAll().size());
    }

    @Test
    void removingEmployeShouldDecreaseSize() {

        Employee e = new Employee();
        e.setName("Test");
        e.setSurname("Testovski");
        service.addEmployee(e);

        int numberOfPreloadedEmployees = service.findAll().size();

        service.removeEmployee(1);

        Assertions.assertEquals(
                numberOfPreloadedEmployees - 1,
                service.findAll().size());
    }

    @Test
    void shouldThrowForNonExistingEmployees() {
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> service.getEmployee(-1));
    }

    @Test
    void employeeDO_ShouldBeEqualToItsEmployeeEntity() {
        Employee employee = new Employee();
        employee.setName("Test");
        employee.setSurname("Testovski");
        Employee persistedEmployee = service.addEmployee(employee);

        Assertions.assertEquals(employee.getName(), persistedEmployee.getName());
        Assertions.assertEquals(employee.getSurname(), persistedEmployee.getSurname());
    }

    @Test
    void employeeShouldNotHaveAnyVacationsOnStart() {
        Employee e = new Employee();
        e.setSurname("Petrovski");
        e.setName("Petar");
        service.addEmployee(e);
        Assertions.assertEquals(0, service.findAllEmployeesVacations(1).size());
    }

    @Test
    void addingVacationShouldNotThrowIfEmployeeExists() {
        Vacation vacation = new Vacation();
        vacation.setStatus(VacationStatus.SUBMITTED);
        vacation.setFrom(LocalDate.now());
        vacation.setTo(LocalDate.now());

        Employee e = new Employee();
        e.setSurname("Employee");
        e.setName("One");
        service.addEmployee(e);

        Employee e2 = new Employee();
        e2.setSurname("Employee");
        e2.setName("Two");
        service.addEmployee(e2);

        service.addVacationToEmployee(1, vacation);
        Assertions.assertEquals(1, service.findAllEmployeesVacations(1).size());
        Assertions.assertEquals(0, service.findAllEmployeesVacations(2).size());

        Vacation persistedVacation =
                service.findAllEmployeesVacations(1).stream().findFirst().get();

        Assertions.assertEquals(1, persistedVacation.getId());
        Assertions.assertEquals(vacation.getStatus(), persistedVacation.getStatus());
        Assertions.assertEquals(vacation.getFrom(), persistedVacation.getFrom());
        Assertions.assertEquals(vacation.getTo(), persistedVacation.getTo());
    }

    @Test
    void addingVacationToNonExistingEmployeeShouldThrow() {

        Vacation vacation = new Vacation();
        vacation.setStatus(VacationStatus.SUBMITTED);
        vacation.setFrom(LocalDate.now());
        vacation.setTo(LocalDate.now());

        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> service.addVacationToEmployee(-1,
                vacation));
    }

    @Test
    void shouldThrowIfRequestedVacationDoesntExists() {

        Employee e = new Employee();
        e.setSurname("Employee");
        e.setName("One");
        service.addEmployee(e);

        Assertions.assertThrows(VacationNotFoundException.class,
                () -> service.getVacationFromEmployee(1, 1));
    }

    @Test
    void removingVacationShouldThrowIfEmployeeDoesntExistsOrVacationDoesntExists() {

        Employee e = new Employee();
        e.setSurname("Employee");
        e.setName("One");
        service.addEmployee(e);

        Assertions.assertThrows(VacationNotFoundException.class,
                () -> service.removeVacationFromEmployee(1, 1));
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> service.removeVacationFromEmployee(-1, 1));
    }

    @Test
    void updatingShouldThrowIfVacationsDoesntExists() {

        Employee e = new Employee();
        e.setSurname("Employee");
        e.setName("One");
        service.addEmployee(e);

        Assertions.assertThrows(VacationNotFoundException.class,
                () -> service.updateVacationForEmployee(1, 1, VacationStatus.APPROVED));
    }

    @Test
    void updatingVacationShouldUpdatePeristedVacation() {
        Vacation vacation = new Vacation();
        vacation.setStatus(VacationStatus.SUBMITTED);
        vacation.setFrom(LocalDate.now());
        vacation.setTo(LocalDate.now());

        Employee e = new Employee();
        e.setSurname("Employee");
        e.setName("One");
        service.addEmployee(e);

        service.addVacationToEmployee(1, vacation);
        service.updateVacationForEmployee(1, 0, VacationStatus.APPROVED);

        Assertions.assertEquals(VacationStatus.APPROVED,
                service.getVacationFromEmployee(1, 0).getStatus());
    }
}
