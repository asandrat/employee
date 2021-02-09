package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@Import(EmployeeRepositoryDBImpl.class)
@DataJpaTest
class EmployeeRepositoryDBImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EmployeeRepositoryDB employeeRepositoryDB;

    @AfterEach //to track database state
    void printNames() {
        employeeRepositoryDB.findAllEmployees()
                .forEach(employee -> System.out.println(employee.getName()));
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(employeeRepositoryDB);
        Assertions.assertEquals(4,
                employeeRepositoryDB.findAllEmployees().size());
    }

    @Test
    void addEmployee() {
        Assertions.assertNotNull(employeeRepositoryDB);
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
    void name() {
        Assertions.assertNotNull(employeeRepositoryDB);
        Assertions.assertEquals("Anica",
                employeeRepositoryDB.findEmployeeById(1).getName());
    }

    @Test
    void findById() {
        Assertions.assertNotNull(employeeRepositoryDB);
        long id = 1;
        Employee employeeDB = testEntityManager.find(Employee.class, id);
        Employee employeeRepo = employeeRepositoryDB.findEmployeeById(id);
        Assertions.assertEquals(employeeDB, employeeRepo);
    }

    @Test
    void delete() {
        Assertions.assertNotNull(employeeRepositoryDB);
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
        Assertions.assertNotNull(employeeRepositoryDB);
        long id = 2;
        Optional<Vacation> vacation =
                employeeRepositoryDB.findAllVacationsOfEmployee(id)
                        .stream().findFirst();
        Assertions.assertTrue(vacation.isPresent());
        Assertions.assertEquals(id, vacation.get().getEmployee().getId());
    }

}