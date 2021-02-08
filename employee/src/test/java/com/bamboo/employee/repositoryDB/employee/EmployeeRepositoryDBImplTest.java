package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
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
        testEntityManager.persist(new Employee("Eva", "Ras"));
        Assertions.assertEquals(oldSize + 1,
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
        long id = 1;
        Employee employee = testEntityManager.find(Employee.class, id);
        Assertions.assertEquals(employee,
                employeeRepositoryDB.findEmployeeById(id));

        employeeRepositoryDB.deleteEmployee(employee);
        Assertions.assertNull(testEntityManager.find(Employee.class, id));
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