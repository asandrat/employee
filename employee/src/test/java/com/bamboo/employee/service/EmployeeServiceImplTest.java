package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.InvalidStateTransitionException;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private VacationValidator vacationValidator;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    ArgumentCaptor<Vacation> vacationArgumentCaptor;

    @Captor
    ArgumentCaptor<Employee> employeeArgumentCaptor;

    @Test
    void Should_DelegateAddEmployeeMethodToEmployeeRepository() {
        employeeService.addEmployee("Jovana", "Jovic");
        verify(employeeRepository).addEmployee(
                employeeArgumentCaptor.capture()
        );
        Employee employee = employeeArgumentCaptor.getValue();
        assertThat(employee.getName()).isEqualTo("Jovana");
        assertThat(employee.getSurname()).isEqualTo("Jovic");
    }

    @Test
    void Should_DelegateRemoveEmployeeMethodToEmployeeRepository() {
        String employeeId = "3f6600da-275b-4b51-9105-ed24fcc650bb";
        employeeService.removeEmployee(employeeId);
        verify(employeeRepository).deleteEmployee(
                stringArgumentCaptor.capture()
        );
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(employeeId);
    }

    @Test
    void Should_DelegateAddVacationMethodToEmployeeRepository() {
        String employeeId = "3f6600da-275b-4b51-9105-ed24fcc650bb";
        employeeService.addVacation(
                employeeId,
                "12/06/2021",
                "18/06/2021",
                "APPROVED"
        );
        verify(employeeRepository).addVacationToEmployee(
                stringArgumentCaptor.capture(),
                vacationArgumentCaptor.capture()
        );

        Vacation vacation = vacationArgumentCaptor.getValue();
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(employeeId);
        assertThat(vacation.getVacationStatus())
                .isEqualTo(VacationStatus.APPROVED);
    }

    @Test
    void Should_DelegateRemoveVacationMethodToEmployeeRepository() {
        String vacationId = "3f6600da-275b-4b51-9105-ed24fcc650bb";
        String employeeId = "3f6600da-275b-4b51-9105-ed24fcc650cc";
        employeeService.removeVacation(vacationId, employeeId);
        verify(employeeRepository).removeVacation(
                stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture()
        );
        List<String> capturedValues = stringArgumentCaptor.getAllValues();
        assertThat(capturedValues.get(0)).isEqualTo(vacationId);
        assertThat(capturedValues.get(1)).isEqualTo(employeeId);
    }

    @Test
    void Should_DelegateApproveVacationActionToEmployeeRepository() {
        String employeeUniqueId = "123AA-bH0A-BBB";
        String vacationId = "aFG-778-iio-9AA";
        Employee employee = new Employee(
                employeeUniqueId,
                "",
                ""
        );
        when(employeeRepository.findEmployee(employeeUniqueId))
                .thenReturn(employee);
        when(employeeRepository.findVacation(employee, vacationId))
                .thenReturn(new Vacation(
                        vacationId,
                        LocalDate.now(),
                        LocalDate.now(),
                        2L,
                        VacationStatus.APPROVED));
        employeeService.approveVacation(vacationId, employeeUniqueId);
        verify(employeeRepository).approveVacation(
                vacationArgumentCaptor.capture()
        );
        Vacation vacation = vacationArgumentCaptor.getValue();
        assertThat(vacation.getUniqueId()).isEqualTo(vacationId);
        assertThat(vacation.getVacationStatus())
                .isEqualTo(VacationStatus.APPROVED);

    }

    @Test
    void Should_DelegateRejectVacationActionToEmployeeRepository() {
        String employeeUniqueId = "BB677-HHH8-BBB";
        String vacationId = "ujh-jk88-BBB-9AA";
        Employee employee = new Employee(
                employeeUniqueId,
                "",
                ""
        );
        when(employeeRepository.findEmployee(employeeUniqueId))
                .thenReturn(employee);
        when(employeeRepository.findVacation(employee, vacationId))
                .thenReturn(new Vacation(
                        vacationId,
                        LocalDate.now(),
                        LocalDate.now().plusDays(3),
                        3L,
                        VacationStatus.REJECTED));
        employeeService.rejectVacation(vacationId, employeeUniqueId);
        verify(employeeRepository).rejectVacation(
                vacationArgumentCaptor.capture()
        );
        Vacation vacation = vacationArgumentCaptor.getValue();
        assertThat(vacation.getUniqueId()).isEqualTo(vacationId);
        assertThat(vacation.getVacationStatus())
                .isEqualTo(VacationStatus.REJECTED);
    }

    @Test
    void Should_ThrowInvalidStateTransitionException() {
        doThrow(new InvalidStateTransitionException(
                "Could not transfer to that state"
        )).when(vacationValidator).validateVacationTransitionStatus(
                        any(Vacation.class),
                        any(VacationStatus.class)
                );
        assertThrows(InvalidStateTransitionException.class,
                () -> vacationValidator.validateVacationTransitionStatus(
                        new Vacation(),
                        VacationStatus.APPROVED
                ));
        verify(vacationValidator).validateVacationTransitionStatus(
                any(Vacation.class),
                any(VacationStatus.class)
        );
    }

}
