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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;


@DataJpaTest
@Import(PersistentEmployeeRepositoryImpl.class) //todo
class PersistentEmployeeRepositoryImplTest {

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
    void shouldPreloadTwoEmployees() {
        // take a look at data.sql
        Assertions.assertEquals(2, repository.findAll().size());
    }

    @Test
    void preloadedEmployeesShouldBeEqualToValuesInsertedInDataSqlFile() {
        Collection<EmployeeEntity> employees = repository.findAll();
        for (EmployeeEntity employeeEntity :employees) {
            Assertions.assertEquals("Petar", employeeEntity.getName());
        }
    }

    @Test
    void addingEmployeeShouldIncreaseSize() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Psd");
        entityManager.persist(employeeEntity);
        Assertions.assertEquals(3, repository.findAll().size());
    }

    @Test
    void removingEmployeeShouldDecreaseTheSize() {
        repository.delete(1);
        Assertions.assertEquals(1, repository.findAll().size());
    }

    @Test
    void deletingEmployeeShouldDeleteVacation() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test");
        employeeEntity.setSurname("Psd");
        entityManager.persist(employeeEntity);

        VacationEntity vacationEntity = new VacationEntity();
        vacationEntity.setStatus(VacationStatus.SUBMITTED);
        vacationEntity.setFrom(LocalDate.now());
        vacationEntity.setTo(LocalDate.now());

        employeeEntity.addVacation(vacationEntity);

        repository.delete(3);
        Assertions.assertNull(repository.findEmployeesVacationById(3, 4).orElse(null));

    }


    @Test
    void addingVacationToEmployeeShouldPersistSuchVacation() {

        VacationEntity vacationEntity = new VacationEntity();
        vacationEntity.setStatus(VacationStatus.SUBMITTED);
        vacationEntity.setFrom(LocalDate.now());
        vacationEntity.setTo(LocalDate.now());

        Optional<EmployeeEntity> entity = repository.read(1);
        entity.get().addVacation(vacationEntity);

        Assertions.assertEquals(1,
                repository.findAllEmployeesVacations(1).size());
    }

    @Test
    void updatingVacationShouldNotChangeNumberOfVacations() {

        VacationEntity vacationEntity = new VacationEntity();
        vacationEntity.setStatus(VacationStatus.SUBMITTED);
        vacationEntity.setFrom(LocalDate.now());
        vacationEntity.setTo(LocalDate.now());

        Optional<EmployeeEntity> entity = repository.read(1);
        // adding vacation to employee
        entity.get().addVacation(vacationEntity);
        // updating vacation
        vacationEntity.setStatus(VacationStatus.APPROVED);

        Assertions.assertEquals(1,
                repository.findAllEmployeesVacations(1).size());

        Assertions.assertEquals(VacationStatus.APPROVED,
                repository.findAllEmployeesVacations(1).stream().findFirst().get().getStatus());
    }
}
