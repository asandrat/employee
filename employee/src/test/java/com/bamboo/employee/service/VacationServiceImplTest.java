package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.VacationNotFoundException;
import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.entity.VacationStatus;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repository.VacationRepository;
import com.bamboo.employee.validator.VacationStateTransitionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationServiceImplTest {

    @Mock
    private VacationStateTransitionValidator validator;

    @Mock
    private VacationRepository vacationRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private VacationServiceImpl vacationService;

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    ArgumentCaptor<Vacation> vacationArgumentCaptor;

    @Captor
    ArgumentCaptor<VacationStatus> vacationStatusArgumentCaptor;

    private VacationDTO vacationDTO;
    private Vacation vacation;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Annie", "Toms");
        vacation = new Vacation(
                LocalDate.now(),
                LocalDate.now(),
                5,
                VacationStatus.APPROVED);
        vacationDTO = new VacationDTO();
        vacationDTO.setId(1);
        vacationDTO.setDateFrom("01/01/2021");
        vacationDTO.setDateTo("06/01/2021");
        vacationDTO.setDuration(5);
        vacationDTO.setVacationStatus("Approved");
        employee.addVacation(vacation);
    }

    @Test
    void addVacationTest() {

        when(employeeService.checkIfEmployeeExists(anyInt()))
                .thenReturn(employee);
        when(vacationRepository.save(any(Vacation.class)))
                .thenReturn(vacation);
        when(modelMapper.map(any(), any())).thenReturn(vacationDTO);

        VacationDTO returnedVacation = vacationService.addVacation(
                3,
                "01/01/2021",
                "06/01/2021",
                "Approved");

        verify(employeeService).checkIfEmployeeExists(
                integerArgumentCaptor.capture()
        );

        assertThat(returnedVacation).isNotNull();
        assertThat(returnedVacation.getDuration()).isEqualTo(5);
        assertThat(returnedVacation.getVacationStatus()).isEqualTo("Approved");
        assertThat(returnedVacation.getDateFrom()).isEqualTo("01/01/2021");
        assertThat(integerArgumentCaptor.getValue()).isEqualTo(3);
    }

    @Test
    void removeVacationTest() {

        doNothing().when(vacationRepository).deleteById(anyInt());
        when(employeeService.checkIfEmployeeExists(anyInt()))
                .thenReturn(employee);
        when(vacationRepository.findById(anyInt()))
                .thenReturn(vacation);

        vacationService.removeVacation(1, 1);

        verify(vacationRepository).deleteById(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue()).isEqualTo(1);
    }

    @Test
    void changeVacationStatusTest() {

        when(employeeService.checkIfEmployeeExists(anyInt()))
                .thenReturn(employee);
        when(vacationRepository.findById(anyInt()))
                .thenReturn(vacation);
        doNothing().when(validator).validateVacationTransitionStatus(
                any(Vacation.class),
                any(VacationStatus.class)
        );
        doNothing().when(vacationRepository).changeVacationStatus(
                any(Vacation.class),
                any(VacationStatus.class)
        );

        vacationService.changeVacationStatus(
                1,
                1,
                VacationStatus.APPROVED
        );

        verify(vacationRepository).changeVacationStatus(
                vacationArgumentCaptor.capture(),
                vacationStatusArgumentCaptor.capture()
        );

        assertThat(vacationArgumentCaptor.getValue()).isEqualTo(vacation);
        assertThat(vacationStatusArgumentCaptor.getValue())
                .isEqualTo(VacationStatus.APPROVED);
    }

    @Test
    void findVacationTest() {

        when(employeeService.checkIfEmployeeExists(anyInt()))
                .thenReturn(employee);
        when(vacationRepository.findById(anyInt()))
                .thenReturn(vacation);
        when(modelMapper.map(any(), any()))
                .thenReturn(vacationDTO);

        VacationDTO returnedVacation = vacationService.findVacation(
                1,
                1);

        assertThat(returnedVacation).isNotNull();
        assertThat(returnedVacation.getDuration()).isEqualTo(5);
    }

    @Test
    void checkIfVacationBelongsToTheEmployeeThrowsExceptionTest() {

        when(vacationRepository.findById(1))
                .thenReturn(null);

        employee.setUniqueId(5);

        Exception exception = assertThrows(
                VacationNotFoundException.class,
                () ->
                        vacationService.checkIfVacationBelongsToTheEmployee(
                                employee,
                                1
                        )
        );

        String expectedMessage = "Could not find vacation with id: 1 " +
                "for the employee with id: 5";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}
