package com.bamboo.employee.service.vacation;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.vacation.VacationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VacationServiceImplTest {
    @Mock
    VacationRepository vacationRepository;

    @InjectMocks
    VacationServiceImpl vacationServiceImpl;

    @Captor
    ArgumentCaptor<String> argumentCaptorString;

    @Captor
    ArgumentCaptor<Vacation> argumentCaptorVacation;

    @Test
    void addVacationToRepository() {
        vacationServiceImpl.addVacation("123", "2021-03-01", "2021-03-05",
                "submitted");

        verify(vacationRepository).addVacationToEmployee(
                argumentCaptorString.capture(),
                argumentCaptorVacation.capture());

        Vacation vacation = argumentCaptorVacation.getValue();
        Assertions.assertEquals("123", vacation.getEmployeeId());
        Assertions.assertEquals(VacationStatus.fromString("SUBMITTED"),
                vacation.getStatus());
    }

    @Test
    void removeVacationFromRepository() {
        vacationServiceImpl.removeVacation("123456");
        verify(vacationRepository).removeVacation(argumentCaptorString.capture());
        Assertions.assertEquals("123456", argumentCaptorString.getValue());
    }

    @Test
    void approveVacation() {
        vacationServiceImpl.approveVacation("789");
        verify(vacationRepository).approveVacation(argumentCaptorString.capture());
        Assertions.assertEquals("789", argumentCaptorString.getValue());
    }

    @Test
    void rejectVacation() {
        vacationServiceImpl.rejectVacation("456");
        verify(vacationRepository).rejectVacation(argumentCaptorString.capture());
        Assertions.assertEquals("456", argumentCaptorString.getValue());
    }


}