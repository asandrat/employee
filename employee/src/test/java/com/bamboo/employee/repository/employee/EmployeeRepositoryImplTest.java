package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class EmployeeRepositoryImplTest {

    private EmployeeRepository repository;


    @BeforeEach
    void init() {
        repository = new EmployeeRepositoryImpl();
    }

    @Test
    void readShouldThrowIfEmployeeDoesntExist() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> repository.read(1));
    }

    @Test
    void createEmployeeShouldIncreaseSizeOfMap() {
        Assertions.assertEquals(0, repository.findAll().size());
        repository.create(new Employee(1, "ads", "sa"));
        Assertions.assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteEmployeeShouldDecreaseSizeOfMap() {
        Assertions.assertEquals(0, repository.findAll().size());
        repository.create(new Employee(1, "ads", "sa"));
        repository.delete(1);
        Assertions.assertEquals(0, repository.findAll().size());
    }

    @Test
    void ShouldNotBeAbleToAddSameEmployeeTwice() {
        Assertions.assertTrue(repository.create(new Employee(1, "ads", "sa")));
        Assertions.assertFalse(repository.create(new Employee(1, "ads", "sa")));
        Assertions.assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldNotBeAbleToDeleteSameEmployeeTwice() {
        Employee e = new Employee(1, "ads", "asd");
        Assertions.assertTrue(repository.create(e));
        Assertions.assertEquals(e, repository.delete(1));
        Assertions.assertNull(repository.delete(1));
    }

    @Test
    void shouldNotBeAbleToDeleteFromEmptyMap() {
        Assertions.assertEquals(0, repository.findAll().size());
        Assertions.assertNull(repository.delete(1));
        Assertions.assertEquals(0, repository.findAll().size());
    }

    @Test
    void shouldNotBeAbleToDeleteNonExistingVacation() {
        Vacation v = new Vacation(new VacationId(1, 1));
        Employee e = new Employee(1, "asd", "asd");
        Assertions.assertTrue(repository.create(e));
        Assertions.assertNull(repository.deleteVacation(v.getId()));
    }

    @Test
    void addingVacationShouldIncreaseSizeOfVacationCollection() {
        Vacation v = new Vacation(new VacationId(1, 1));
        Employee e = new Employee(1, "asd", "asd");
        Assertions.assertTrue(repository.create(e));
        Assertions.assertEquals(0,
                repository.read(1).get().getVacations().size());
        repository.addVacationToEmployee(v);
        Assertions.assertEquals(1,
                repository.read(1).get().getVacations().size());
    }

    @Test
    void deletingVacationShouldDecreaseSizeOfVacationCollection() {
        Vacation v = new Vacation(new VacationId(1, 1));
        Employee e = new Employee(1, "asd", "asd");
        Assertions.assertTrue(repository.create(e));
        repository.addVacationToEmployee(v);
        Assertions.assertEquals(1,
                repository.read(1).get().getVacations().size());
        Assertions.assertEquals(v, repository.deleteVacation(v.getId()));
        Assertions.assertEquals(0,
                repository.read(1).get().getVacations().size());
    }


}
