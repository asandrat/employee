package com.bamboo.employee.service.vacation;

import com.bamboo.employee.model.VacationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
class VacationServiceImplTest {

    @Autowired
    private VacationService vacationService;

    @AfterEach// to track database state
    void printIds() {
        vacationService.findAll()
                .forEach(vacation -> System.out.println(vacation.getId()));
    }

    @BeforeEach
    void addRecordsToDB() {
        vacationService.addVacation(
                "1", "2021-11-01", "2021-11-10", "SUBMITTED");
        vacationService.addVacation(
                "1", "2021-10-01", "2021-10-10", "SUBMITTED");
        vacationService.addVacation(
                "2", "2021-09-01", "2021-09-10", "SUBMITTED");
        vacationService.addVacation(
                "3", "2021-08-01", "2021-08-10", "SUBMITTED");
    }

    @Test
    void add() {
        int oldSize = vacationService.findAll().size();
        VacationDTO vacationDTO = vacationService.addVacation(
                "1", "2022-01-01", "2022-01-10", "SUBMITTED");
        Assertions.assertEquals(oldSize + 1, vacationService.findAll().size());
        Assertions.assertEquals("2022-01-01", vacationDTO.getDateFrom());
        Assertions.assertEquals("2022-01-10", vacationDTO.getDateTo());
        vacationService.removeVacation(vacationDTO.getId());
        Assertions.assertEquals(oldSize, vacationService.findAll().size());
    }


    @Test
    void remove() {
        int oldSize = vacationService.findAll().size();
        VacationDTO vacationDTO = vacationService.addVacation(
                "1", "2022-01-01", "2022-01-10", "SUBMITTED");
        Assertions.assertEquals(oldSize + 1, vacationService.findAll().size());
        vacationService.removeVacation(vacationDTO.getId());
        Assertions.assertEquals(oldSize, vacationService.findAll().size());
    }

    @Test
    void findAll() {
        Collection<VacationDTO> vacations = vacationService.findAll();
        Set<String> dateFromSet =
                vacations.stream().map(VacationDTO::getDateFrom).collect(Collectors.toSet());
        Assertions.assertNotNull(vacations);
        Assertions.assertTrue(dateFromSet.containsAll(Arrays.asList(
                "2021-11-01", "2021-10-01", "2021-09-01",
                "2021-08-01")));

    }


    @Test
    void approve() {
        int oldSize = vacationService.findAll().size();
        VacationDTO vacationDTO = vacationService.addVacation(
                "1", "2022-01-01", "2022-01-10", "SUBMITTED");
        Assertions.assertEquals("SUBMITTED", vacationDTO.getStatus());
        Assertions.assertEquals(oldSize + 1, vacationService.findAll().size());
        vacationService.approveVacation(vacationDTO.getId());
        VacationDTO approvedVacation =
                vacationService.findById(vacationDTO.getId());
        Assertions.assertEquals("APPROVED", approvedVacation.getStatus());
        vacationService.removeVacation(vacationDTO.getId());
        Assertions.assertEquals(oldSize, vacationService.findAll().size());
    }

    @Test
    void reject() {
        VacationDTO vacationDTO = vacationService.addVacation(
                "1", "2022-01-01", "2022-01-10", "SUBMITTED");
        Assertions.assertEquals("SUBMITTED", vacationDTO.getStatus());
        int oldSize = vacationService.findAll().size();
        vacationService.rejectVacation(vacationDTO.getId());
        Assertions.assertEquals(oldSize - 1, vacationService.findAll().size());
    }

}