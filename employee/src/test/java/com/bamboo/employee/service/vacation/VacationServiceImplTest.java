package com.bamboo.employee.service.vacation;

import com.bamboo.employee.entities.Vacation;
import com.bamboo.employee.entities.VacationStatus;
import com.bamboo.employee.repository.vacation.VacationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    void saveAllVacationsToRepository() {
        Map<String, Vacation> vacationMap = new HashMap<>();
        vacationMap.put("456",
                new Vacation("456", "123",
                LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 3, 5),
                5, VacationStatus.fromString("submitted")));
        vacationServiceImpl.saveAllVacations(vacationMap);
        verify(vacationRepository).saveAllVacations(vacationMap);
    }

    @Test
    void findAllVacationsFromRepository(){
        vacationServiceImpl.findAll();
        verify(vacationRepository).findAll();
    }

}