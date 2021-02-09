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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootTest
@Transactional // for rolling back after each test
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
    void shouldReadFromDataSql() {
        Assertions.assertNotEquals(0, service.findAll().size());
    }

    @Test
    void removingEmployeShouldDecreaseSize() {

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
        Assertions.assertEquals(0, service.findAllEmployeesVacations(1).size());
    }

    @Test
    void addingVacationShouldNotThrowIfEmployeeExists() {
        Vacation vacation = new Vacation();
        vacation.setStatus(VacationStatus.SUBMITTED);
        vacation.setFrom(LocalDate.now());
        vacation.setTo(LocalDate.now());

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
        Assertions.assertThrows(VacationNotFoundException.class,
                () -> service.getVacationFromEmployee(1, 1));
    }

    @Test
    void removingVacationShouldThrowIfEmployeeDoesntExistsOrVacationDoesntExists() {

        Assertions.assertThrows(VacationNotFoundException.class,
                () -> service.removeVacationFromEmployee(1, 1));
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> service.removeVacationFromEmployee(-1, 1));
    }

    @Test
    void updatingShouldThrowIfVacationsDoesntExists() {

        Assertions.assertThrows(VacationNotFoundException.class,
                () -> service.updateVacationForEmployee(1, 1, VacationStatus.APPROVED));
    }

    @Test
    void updatingVacationShouldUpdatePeristedVacation() {
        Vacation vacation = new Vacation();
        vacation.setStatus(VacationStatus.SUBMITTED);
        vacation.setFrom(LocalDate.now());
        vacation.setTo(LocalDate.now());

        service.addVacationToEmployee(1, vacation);
        /*
        vacationId is 0 due to added vacation isn't instantly saved in DB
        need to fix this or its probably just bad design
         */
        service.updateVacationForEmployee(1, 0, VacationStatus.APPROVED);

        Assertions.assertEquals(VacationStatus.APPROVED,
                service.getVacationFromEmployee(1, 0).getStatus());
    }
}
