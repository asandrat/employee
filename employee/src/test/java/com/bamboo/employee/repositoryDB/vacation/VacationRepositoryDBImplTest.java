package com.bamboo.employee.repositoryDB.vacation;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

@Import(VacationRepositoryDBImpl.class)
@DataJpaTest
class VacationRepositoryDBImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private VacationRepositoryDB vacationRepositoryDB;

    @AfterEach // to track database state
    void printIds() {
        vacationRepositoryDB.findAllVacations()
                .forEach(vacation -> System.out.println(vacation.getId()));
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(vacationRepositoryDB);
        Assertions.assertEquals(5,
                vacationRepositoryDB.findAllVacations().size());
    }

    @Test
    void add() {
        Assertions.assertNotNull(vacationRepositoryDB);
        int oldSize = vacationRepositoryDB.findAllVacations().size();
        long employeeId = 1;
        Employee employee = testEntityManager.find(Employee.class, employeeId);
        Vacation addedVacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                "SUBMITTED"));
        Assertions.assertEquals(oldSize + 1,
                vacationRepositoryDB.findAllVacations().size());
        vacationRepositoryDB.deleteVacation(addedVacation);
        Assertions.assertEquals(oldSize,
                vacationRepositoryDB.findAllVacations().size());
    }

    @Test
    void findById() {
        Assertions.assertNotNull(vacationRepositoryDB);
        long id = 1;
        Vacation vacationDB = testEntityManager.find(Vacation.class, id);
        Vacation vacationRepo = vacationRepositoryDB.findVacationById(id);
        Assertions.assertEquals(vacationDB, vacationRepo);
    }

    @Test
    void delete() {
        Assertions.assertNotNull(vacationRepositoryDB);
        int oldSize = vacationRepositoryDB.findAllVacations().size();
        long employeeId = 1;
        Employee employee = testEntityManager.find(Employee.class, employeeId);
        Vacation addedVacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                "SUBMITTED"));
        Assertions.assertEquals(oldSize + 1,
                vacationRepositoryDB.findAllVacations().size());
        vacationRepositoryDB.deleteVacation(addedVacation);
        Assertions.assertNull(testEntityManager.find(Vacation.class,
                addedVacation.getId()));
        Assertions.assertEquals(oldSize,
                vacationRepositoryDB.findAllVacations().size());
    }

    @Test
    void approve() {
        Assertions.assertNotNull(vacationRepositoryDB);
        long id = 1;
        Vacation vacation = vacationRepositoryDB.findVacationById(id);
        Assertions.assertEquals("SUBMITTED", vacation.getStatus());
        vacationRepositoryDB.approveVacation(vacation);
        testEntityManager.clear();
        testEntityManager.flush();
        Vacation approvedVacation = testEntityManager.find(Vacation.class, id);
        Assertions.assertEquals("APPROVED", approvedVacation.getStatus());
    }

    @Test
    void reject() {
        Assertions.assertNotNull(vacationRepositoryDB);
        int oldSize = vacationRepositoryDB.findAllVacations().size();
        long employeeId = 1;
        Employee employee = testEntityManager.find(Employee.class, employeeId);
        Vacation addedVacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                "SUBMITTED"));
        Assertions.assertEquals(oldSize + 1,
                vacationRepositoryDB.findAllVacations().size());
        vacationRepositoryDB.rejectVacation(addedVacation);
        Assertions.assertNull(testEntityManager.find(Vacation.class,
                addedVacation.getId()));
        Assertions.assertEquals(oldSize,
                vacationRepositoryDB.findAllVacations().size());
    }


}