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
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService service;

    @Test
    void sanityCheck() {
        Assertions.assertNotNull(service);
    }


    @Test
    void addingEmployeeShouldIncreaseSize() {
        Employee employee = new Employee();
        employee.setName("Test");
        employee.setSurname("Testovski");
        service.addEmployee(employee);
        Assertions.assertEquals(3, service.findAll().size());
    }

    @Test
    void shouldFetchTwoEmployees() {
        Assertions.assertEquals(2, service.findAll().size());
    }

    @Test
    void removingEmployeShouldDecreaseSize() {
        service.removeEmployee(1);
        Assertions.assertEquals(1, service.findAll().size());
    }

    @Test
    void shouldThrowForNonExistingEmployees() {
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> service.getEmployee(42));
    }

    @Test
    void employeeDO_ShouldBeEqualToItsEmployeeEntity() {
        Employee employee = new Employee();
        employee.setName("Test");
        employee.setSurname("Testovski");
        service.addEmployee(employee);

        Employee persistedEmployee = service.getEmployee(3);
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
                () -> service.addVacationToEmployee(42,
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
                () -> service.removeVacationFromEmployee(42, 1));
    }

    @Test
    void updatingShouldThrowIfVacationsDoesntExists() {

        Assertions.assertThrows(VacationNotFoundException.class,
                () -> service.updateVacationForEmployee(1, 1, VacationStatus.APPROVED));
    }

    @Test
    void updatingVacationShouldUpdatePeristedVacation() {
        Vacation vacation = new Vacation();
        vacation.setId(1); //todo
        vacation.setStatus(VacationStatus.SUBMITTED);
        vacation.setFrom(LocalDate.now());
        vacation.setTo(LocalDate.now());

        service.addVacationToEmployee(1, vacation);
        service.updateVacationForEmployee(1, 1, VacationStatus.APPROVED);
    }
}
