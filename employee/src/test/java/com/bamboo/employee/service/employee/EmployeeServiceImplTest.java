package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    private Vacation v;

    @BeforeEach
    void init() {
        v = new Vacation(
                1,
                1, LocalDate.parse("2021-01-01"),
                LocalDate.parse("2021-02-01"),
                null,
                VacationStatus.SUBMITTED);
    }

    @Test
    void getEmployeeShouldDelegateToRepositoryRead() {
        int id = 1;
        Employee e = new Employee(id, "Petar", "Petrovski");

        when(repository.read(id)).thenReturn(Optional.of(e));

        Assertions.assertEquals(e, service.getEmployee(id));
        Assertions.assertNotEquals(e, service.getEmployee(2));

        verify(repository, times(2)).read(anyInt());
    }

    @Test
    void addEmployeeShouldDelegateToRepositoryCreate() {
        Employee e = new Employee(1, "Petar", "Petrovski");
        when(repository.create(e)).thenReturn(true);

        Assertions.assertTrue(service.addEmployee(e));

        // can't add 2 same employees
        when(repository.create(e)).thenReturn(false);
        Assertions.assertFalse(service.addEmployee(e));

        verify(repository, times(2)).create(any(Employee.class));
    }

    @Test
    void addEmployeeShouldNotCreateDefaultVacation() {
        Employee e = new Employee(1, "Petar", "Petrovski");
        when(repository.create(e)).thenReturn(true);
        Assertions.assertTrue(service.addEmployee(e));
        verify(repository, never()).addVacationToEmployee(any(Vacation.class));
    }

    @Test
    void removeEmployeeShouldDelegateToRepositoryDelete() {
        Employee e = new Employee(1, "Petar", "Petrovski");
        when(repository.delete(1)).thenReturn(Optional.of(e));

        Assertions.assertEquals(Optional.of(e).get(), service.removeEmployee(1));

    }

    @Test
    void addVacationShouldDelegateToRepository() {
        Employee e = new Employee(1, "as", "asd");
        doNothing().when(repository).addVacationToEmployee(v);
        when(repository.read(1)).thenReturn(Optional.of(e));
        Assertions.assertDoesNotThrow(() -> service.addVacationToEmployee(v));
        verify(repository).addVacationToEmployee(any(Vacation.class));
    }

    @Test
    void addVacationShouldThrowIfEmpDoesntExist() {
        when(repository.read(any(Integer.class))).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.addVacationToEmployee(v));
        verify(repository, never()).addVacationToEmployee(any(Vacation.class));
    }

    @Test
    void shouldApproveSubmittedVacations() {
        VacationId id = new VacationId(1, 1);
        Employee e = new Employee(1, "petar", "testovski");
        e.addVacation(v);
        when(repository.read(1)).thenReturn(Optional.of(e));

        Assertions.assertTrue(service.approveVacationForEmployee(id));
        verify(repository).update(id, VacationStatus.APPROVED);
    }

    @Test
    void shouldRejectSubmittedVacations() {
        VacationId id = new VacationId(1, 1);
        Employee e = new Employee(1, "petar", "testovski");
        e.addVacation(v);
        when(repository.read(1)).thenReturn(Optional.of(e));

        Assertions.assertTrue(service.rejectVacationForEmployee(id));
        verify(repository, never()).update(id, VacationStatus.APPROVED);
    }

    @Test
    void shouldFailToModifyAlreadyModifiedVacation() {
        VacationId id = new VacationId(1, 1);
        v.setStatus(VacationStatus.APPROVED);
        Employee e = new Employee(1, "petar", "testovski");
        e.addVacation(v);
        when(repository.read(1)).thenReturn(Optional.of(e));

        Assertions.assertFalse(service.approveVacationForEmployee(id));

        v.setStatus(VacationStatus.REJECTED);
        e.removeVacation(id);
        e.addVacation(v);
        Assertions.assertFalse(service.approveVacationForEmployee(id));

        v.setStatus(VacationStatus.APPROVED);
        e.removeVacation(id);
        e.addVacation(v);
        Assertions.assertFalse(service.rejectVacationForEmployee(id));

        v.setStatus(VacationStatus.REJECTED);
        e.removeVacation(id);
        e.addVacation(v);
        Assertions.assertFalse(service.rejectVacationForEmployee(id));
    }


    @Test
    void shouldDelegateDeletionOfVacationToRepository() {
        VacationId id = new VacationId(1, 1);
        when(repository.deleteVacation(id)).thenReturn(new Vacation(id));
        service.removeVacationFromEmployee(id);
    }
}
