package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    @Test
    void getEmployeeShouldDelegateToRepositoryRead() {
        int id = 1;
        Employee e = new Employee(id, "Petar", "Petrovski");

        when(repository.read(id)).thenReturn(e);

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
        verify(repository, never()).addVacationToEmployee(any(Vacation.class));
    }

    @Test
    void removeEmployeeShouldDelegateToRepositoryDelete() {
        when(repository.delete(1)).thenReturn(true);

        Assertions.assertTrue(service.removeEmployee(1));

        verify(repository).delete(1);

        // can't delete same employee multiple times
        when(repository.delete(1)).thenReturn(false);
        Assertions.assertFalse(service.removeEmployee(1));
    }
}
