package com.bamboo.employee.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee e;

    @BeforeEach
    void init() {
        e = new Employee(1, "name", "surname");
    }


    @Test
    void addVacationShouldIncreaseSizeOfVacationCollection() {
        Vacation v = new Vacation(new VacationId(1, 1));
        Assertions.assertEquals(0, e.getVacations().size());
        Assertions.assertDoesNotThrow(() -> e.addVacation(v));
        Assertions.assertEquals(1, e.getVacations().size());
    }

    @Test
    void addVacationShouldMakeCopyOfPassedVacation() {
        Vacation v = new Vacation(new VacationId(1, 1));
        e.addVacation(v);
        assert e.getVacation(new VacationId(1, 1)) != v;
    }

    @Test
    void removeVacationShouldDecreaseSizeOfVacationCollection() {
        VacationId id = new VacationId(1, 1);
        Vacation v = new Vacation(id);
        e.addVacation(v);
        Assertions.assertEquals(1, e.getVacations().size());
        e.removeVacation(id);
        Assertions.assertEquals(0, e.getVacations().size());
    }

    @Test
    void shouldReturnNullOnAttemptToDeleteNonExistingVacation() {
        Assertions.assertNull(e.removeVacation(new VacationId(1, 1)));
    }

    @Test
    void shouldThrowIfSameVacationIsAdded() {
        VacationId id = new VacationId(1, 1);
        Vacation v = new Vacation(id);
        e.addVacation(v);
        Assertions.assertNull(e.addVacation(v));
    }

    @Test
    void shouldThrowIfVacationDoesntExist() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> e.getVacation(new VacationId(1, 1)));
    }


    @Test
    void shouldNotThrowIfVacationExists() {
        e.addVacation(new Vacation(new VacationId(1,1))) ;
        Assertions.assertDoesNotThrow(() -> e.getVacation(new VacationId(1,
                1)));
    }

    @Test
    void updateVacationShouldThrowIfVacationDoesntExist() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> e.updateVacation(new VacationId(1, 1),VacationStatus.REJECTED));
    }

    @Test
    void updateVacationShouldModifyVacation() {
        VacationId id = new VacationId(1, 1);
        Vacation v = new Vacation(1, 1, LocalDate.now(),
                LocalDate.now(), null, VacationStatus.SUBMITTED);
        e.addVacation(v);
        e.updateVacation(id, VacationStatus.REJECTED);
        Assertions.assertEquals(VacationStatus.REJECTED,
                e.getVacation(id).getStatus());
    }
}
