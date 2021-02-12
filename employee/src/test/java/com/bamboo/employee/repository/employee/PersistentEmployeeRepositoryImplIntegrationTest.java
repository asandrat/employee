package com.bamboo.employee.repository.employee;


import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.entity.VacationEntity;
import com.bamboo.employee.model.VacationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;


@DataJpaTest
@Import(PersistentEmployeeRepositoryImpl.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PersistentEmployeeRepositoryImplIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository repository;

    @Test
    void shouldAutoWireRepositoryAndEntityManager() {
        Assertions.assertNotNull(repository);
        Assertions.assertNotNull(entityManager);
    }

    @Test
    void employeeTableShouldBeEmptyOnStart() {
        Assertions.assertEquals(0, repository.findAll().size());
    }

    @Test
    void preloadedEmployeesShouldBeEqualToValuesInsertedInDataSqlFile() {
        Collection<EmployeeEntity> employees = repository.findAll();
        for (EmployeeEntity employeeEntity : employees) {
            Assertions.assertEquals("Petar", employeeEntity.getName());
        }
    }

    @Test
    void addingEmployeeShouldIncreaseSize() {

        int sizeWithoutNewEmployee = repository.findAll().size();

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Psd");
        repository.create(employeeEntity);

        Assertions.assertEquals(
                sizeWithoutNewEmployee + 1,
                repository.findAll().size());
    }

    @Test
    void removingEmployeeShouldDecreaseTheSize() {

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Psd");
        repository.create(employeeEntity);

        int numberOfPreloadedEmployees = repository.findAll().size();

        repository.delete(1);
        Assertions.assertEquals(numberOfPreloadedEmployees - 1,
                repository.findAll().size());
    }

    @Test
    void deletingEmployeeShouldDeleteVacation() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Test");

        VacationEntity vacationEntity = new VacationEntity();
        vacationEntity.setStatus(VacationStatus.SUBMITTED);
        vacationEntity.setFrom(LocalDate.now());
        vacationEntity.setTo(LocalDate.now());

        employeeEntity.addVacation(vacationEntity);

        entityManager.persist(employeeEntity);

        entityManager.detach(employeeEntity);

        Assertions.assertFalse(entityManager.getEntityManager().contains(vacationEntity));

    }


    @Test
    void addingVacationToEmployeeShouldPersistSuchVacation() {


        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Test");
        repository.create(employeeEntity);

        Optional<EmployeeEntity> entity = repository.read(1);

        VacationEntity vacationEntity = new VacationEntity();
        vacationEntity.setStatus(VacationStatus.SUBMITTED);
        vacationEntity.setFrom(LocalDate.now());
        vacationEntity.setTo(LocalDate.now());
        entity.get().addVacation(vacationEntity);

        Assertions.assertEquals(1,
                repository.findAllEmployeesVacations(1).size());
    }

    @Test
    void updatingVacationShouldNotChangeNumberOfVacations() {

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Test");
        repository.create(employeeEntity);

        Optional<EmployeeEntity> entity = repository.read(1);

        VacationEntity vacationEntity = new VacationEntity();
        vacationEntity.setStatus(VacationStatus.SUBMITTED);
        vacationEntity.setFrom(LocalDate.now());
        vacationEntity.setTo(LocalDate.now());
        // adding vacation to employee
        entity.get().addVacation(vacationEntity);
        // updating vacation
        vacationEntity.setStatus(VacationStatus.APPROVED);

        Assertions.assertEquals(1,
                repository.findAllEmployeesVacations(1).size());

        Assertions.assertEquals(VacationStatus.APPROVED,
                repository.findAllEmployeesVacations(1).stream().findFirst().get().getStatus());
    }


    @Test
    void persistingEmployeeShouldPersistVacation() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Test");

        VacationEntity vacationEntity = new VacationEntity();
        vacationEntity.setStatus(VacationStatus.SUBMITTED);
        vacationEntity.setFrom(LocalDate.now());
        vacationEntity.setTo(LocalDate.now());

        employeeEntity.addVacation(vacationEntity);

        entityManager.persist(employeeEntity);

        Assertions.assertTrue(entityManager.getEntityManager().contains(vacationEntity));
    }

}
