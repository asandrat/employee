package com.bamboo.employee.service.vacation;

import com.bamboo.employee.model.VacationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class VacationServiceImplTest {

    @Autowired
    private VacationService vacationService;

    @Test
    void add() {
        VacationDTO vacationDTO = vacationService.addVacation(
                "1", "2022-01-01", "2022-01-10", "SUBMITTED");
        Assertions.assertEquals("2022-01-01", vacationDTO.getDateFrom());
        Assertions.assertEquals("2022-01-10", vacationDTO.getDateTo());
    }


    @Test
    void remove() {
        VacationDTO vacationDTO = vacationService.addVacation(
                "1", "2022-01-01", "2022-01-10", "SUBMITTED");
        int oldSize = vacationService.findAll().size();
        vacationService.removeVacation(vacationDTO.getId());
        Assertions.assertEquals(oldSize - 1, vacationService.findAll().size());
    }

    @Test
    void findAll() {
        Collection<VacationDTO> vacations = vacationService.findAll();
        Set<String> dateFromSet =
                vacations.stream().map(VacationDTO::getDateFrom).collect(Collectors.toSet());
        Assertions.assertNotNull(vacations);
        Assertions.assertTrue(dateFromSet.containsAll(Arrays.asList(
                "2021-03-01", "2021-04-01", "2021-05-01",
                "2021-06-01", "2021-07-01")));

    }

    @Test
    void approve() {
        VacationDTO vacationDTO = vacationService.addVacation(
                "1", "2022-01-01", "2022-01-10", "SUBMITTED");
        Assertions.assertEquals("SUBMITTED", vacationDTO.getStatus());
        vacationService.approveVacation(vacationDTO.getId());
        VacationDTO approvedVacation =
                vacationService.findById(vacationDTO.getId());
        Assertions.assertEquals("APPROVED", approvedVacation.getStatus());
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