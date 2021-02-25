package com.bamboo.employee.repositoryDB.vacation;

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

@Import(VacationRepositoryDBImpl.class)
@DataJpaTest
@ActiveProfiles("test")
class VacationRepositoryDBImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private VacationRepositoryDB vacationRepositoryDB;

    @BeforeEach
    void addRecordsToDatabase() {
        Employee employee = new Employee("Ivo", "Andric");
        testEntityManager.persist(employee);
        Vacation vacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                VacationStatus.fromString("SUBMITTED")));
    }

    @AfterEach
        // to track database state
    void printIds() {
        vacationRepositoryDB.findAllVacations()
                .forEach(vacation -> System.out.println(vacation.getId()));
    }

    @Test
    void findAll() {
        Assertions.assertEquals(1,
                vacationRepositoryDB.findAllVacations().size());
    }

    @Test
    void add() {
        int oldSize = vacationRepositoryDB.findAllVacations().size();
        long employeeId = 1;
        Employee employee = testEntityManager.find(Employee.class, employeeId);
        Vacation addedVacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                VacationStatus.fromString("SUBMITTED")));
        Assertions.assertEquals(oldSize + 1,
                vacationRepositoryDB.findAllVacations().size());
        vacationRepositoryDB.deleteVacation(addedVacation);
        Assertions.assertEquals(oldSize,
                vacationRepositoryDB.findAllVacations().size());
    }

    @Test
    void findById() {
        long id = 1;
        Vacation vacationDB = testEntityManager.find(Vacation.class, id);
        Vacation vacationRepo = vacationRepositoryDB.findVacationById(id);
        Assertions.assertEquals(vacationDB, vacationRepo);
    }

    @Test
    void delete() {
        int oldSize = vacationRepositoryDB.findAllVacations().size();
        long employeeId = 1;
        Employee employee = testEntityManager.find(Employee.class, employeeId);
        Vacation addedVacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                VacationStatus.fromString("SUBMITTED")));
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
        long employeeId = 13;
        Employee employee = testEntityManager.find(Employee.class, employeeId);
        Vacation addedVacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                VacationStatus.fromString("SUBMITTED")));
        Assertions.assertEquals(VacationStatus.fromString("SUBMITTED"),
                addedVacation.getStatus());
        vacationRepositoryDB.approveVacation(addedVacation);
        testEntityManager.clear();
        testEntityManager.flush();
        Vacation approvedVacation =
                vacationRepositoryDB.findVacationById(addedVacation.getId());
        Assertions.assertEquals(VacationStatus.fromString("APPROVED"),
                approvedVacation.getStatus());
    }

    @Test
    void reject() {
        int oldSize = vacationRepositoryDB.findAllVacations().size();
        long employeeId = 13;
        Employee employee = testEntityManager.find(Employee.class, employeeId);
        Vacation addedVacation = testEntityManager.persist(new Vacation(
                employee,
                LocalDate.of(2021, 4, 10),
                LocalDate.of(2021, 4, 14),
                VacationStatus.fromString("SUBMITTED")));
        Assertions.assertEquals(oldSize + 1,
                vacationRepositoryDB.findAllVacations().size());
        vacationRepositoryDB.rejectVacation(addedVacation);
        Assertions.assertNull(testEntityManager.find(Vacation.class,
                addedVacation.getId()));
        Assertions.assertEquals(oldSize,
                vacationRepositoryDB.findAllVacations().size());
    }


}