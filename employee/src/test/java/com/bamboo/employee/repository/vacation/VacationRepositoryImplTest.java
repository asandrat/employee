package com.bamboo.employee.repository.vacation;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.FileReaderAndWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class VacationRepositoryImplTest {
    @Mock
    FileReaderAndWriter fileReaderAndWriter;

    @InjectMocks
    VacationRepositoryImpl vacationRepositoryImpl;

    @BeforeEach
    void setVacationRepositoryImpl() {
        vacationRepositoryImpl.init();
    }

    @Test
    void addVacationToEmployee() {
        Vacation vacation = new Vacation("10", "20",
                LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 3, 5),
                5, VacationStatus.fromString("submitted"));
        vacationRepositoryImpl.addVacationToEmployee(vacation);
        Assertions.assertEquals(VacationStatus.fromString("SUBMITTED"),
                vacationRepositoryImpl.findVacation("10").getStatus());
    }

    @Test
    void removeVacation() {
        vacationRepositoryImpl.removeVacation("10");
        Assertions.assertNull(vacationRepositoryImpl.findVacation("10"));
    }

    @Test
    void findVacation() {
        Vacation vacation = new Vacation("30", "40",
                LocalDate.of(2022, 3, 1),
                LocalDate.of(2022, 3, 10),
                10, VacationStatus.fromString("submitted"));
        vacationRepositoryImpl.addVacationToEmployee(vacation);
        Assertions.assertEquals(10,
                vacationRepositoryImpl.findVacation("30").getDuration());
    }

    @Test
    void approveVacation() {
        Vacation vacation = new Vacation("50", "60",
                LocalDate.of(2021, 7, 1),
                LocalDate.of(2021, 7, 10),
                10, VacationStatus.fromString("submitted"));
        vacationRepositoryImpl.addVacationToEmployee(vacation);
        vacationRepositoryImpl.approveVacation("50");
        Assertions.assertEquals(VacationStatus.fromString("APPROVED"),
                vacationRepositoryImpl.findVacation("50").getStatus());
    }

    @Test
    void rejectVacation() {
        Vacation vacation = new Vacation("70", "80",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 10),
                10, VacationStatus.fromString("submitted"));
        vacationRepositoryImpl.addVacationToEmployee(vacation);
        vacationRepositoryImpl.rejectVacation("70");
        Assertions.assertNull(vacationRepositoryImpl.findVacation("70"));
    }

}