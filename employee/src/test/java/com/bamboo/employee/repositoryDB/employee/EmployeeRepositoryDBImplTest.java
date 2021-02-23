package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.entitiesDB.VacationStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Import(EmployeeRepositoryDBImpl.class)
@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryDBImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EmployeeRepositoryDB employeeRepositoryDB;

    @BeforeEach
    void addRecordsToDB() {
        Employee employee1 = testEntityManager.persist(
                new Employee("Eva", "Longoria"));
        Employee employee2 = testEntityManager.persist(
                new Employee("Sarah", "Kay"));
        Employee employee3 = testEntityManager.persist(
                new Employee("Nicolas", "Cage"));
        Employee employee4 = testEntityManager.persist(
                new Employee("Will", "Smith"));
    }

    @AfterEach
        //to track database state
    void printNames() {
        employeeRepositoryDB.findAllEmployees()
                .forEach(employee -> System.out.println(employee.getName()));
    }

    @Test
    void findAll() {
        Assertions.assertEquals(4,
                employeeRepositoryDB.findAllEmployees().size());
    }

    @Test
    void addEmployee() {
        int oldSize = employeeRepositoryDB.findAllEmployees().size();
        Employee addedEmployee = testEntityManager.persist(
                new Employee("Eva", "Ras"));
        Assertions.assertEquals(oldSize + 1,
                employeeRepositoryDB.findAllEmployees().size());
        employeeRepositoryDB.deleteEmployee(addedEmployee);
        Assertions.assertEquals(oldSize,
                employeeRepositoryDB.findAllEmployees().size());
    }

    @Test
    void findById() {
        long id = 1;
        Employee employeeDB = testEntityManager.find(Employee.class, id);
        Employee employeeRepo = employeeRepositoryDB.findEmployeeById(id);
        Assertions.assertEquals(employeeDB, employeeRepo);
    }

    @Test
    void delete() {
        int oldSize = employeeRepositoryDB.findAllEmployees().size();
        Employee addedEmployee = testEntityManager.persist(
                new Employee("Eva", "Ras"));
        Assertions.assertEquals(oldSize + 1,
                employeeRepositoryDB.findAllEmployees().size());
        employeeRepositoryDB.deleteEmployee(addedEmployee);
        Assertions.assertNull(testEntityManager.find(
                Employee.class, addedEmployee.getId()));
        Assertions.assertEquals(oldSize,
                employeeRepositoryDB.findAllEmployees().size());
    }

    @Test
    void findVacations() {
        Optional<Employee> employee = employeeRepositoryDB
                .findAllEmployees().stream().findFirst();
        Assertions.assertTrue(employee.isPresent());
        System.out.println(employee.get().getId());
        Vacation vacation = new Vacation(
                employee.get(),
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 10),
                VacationStatus.fromString("SUBMITTED"));
        employee.get().setVacations(Collections.singletonList(vacation));
        long employeeId = employee.get().getId();
        Optional<Vacation> foundVacation =
                employeeRepositoryDB.findAllVacationsOfEmployee(employeeId)
                        .stream().findFirst();
        Assertions.assertTrue(foundVacation.isPresent());
        Assertions.assertEquals(employeeId,
                foundVacation.get().getEmployee().getId());
    }

}