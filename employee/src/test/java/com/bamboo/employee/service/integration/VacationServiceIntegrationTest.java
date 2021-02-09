package com.bamboo.employee.service.integration;

import com.bamboo.employee.custom.exception.ApplicationException;
import com.bamboo.employee.custom.exception.VacationNotFoundException;
import com.bamboo.employee.entity.VacationStatus;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.service.EmployeeService;
import com.bamboo.employee.service.VacationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class VacationServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationService vacationService;

    private EmployeeDTO employee;

    @BeforeEach
    void setUp() {
        employee = employeeService.addEmployee(
                "Selena",
                "Williams"
        );
    }

    @Test
    public void addVacationTest() {
        VacationDTO vacationDTO = vacationService.addVacation(
                employee.getId(),
                "21/02/2021",
                "25/02/2021",
                "SUBMITTED"
        );

        assertThat(vacationDTO).isNotNull();
        assertThat(vacationDTO.getId()).isNotNull();
        assertThat(vacationDTO.getVacationStatus()).isEqualTo("SUBMITTED");
        assertThat(vacationDTO.getDuration()).isEqualTo(4);
    }

    @Test
    public void removeVacationTest() {
        VacationDTO vacationDTO = vacationService.addVacation(
                employee.getId(),
                "21/02/2021",
                "25/02/2021",
                "APPROVED"
        );

        vacationService.removeVacation(
                vacationDTO.getId(),
                employee.getId()
        );
    }

    @Test
    public void findVacationTest() {
        VacationDTO vacationDTO = vacationService.addVacation(
                employee.getId(),
                "21/02/2021",
                "26/02/2021",
                "APPROVED"
        );

        VacationDTO returnedVacation = vacationService.findVacation(
                employee.getId(),
                vacationDTO.getId()
        );
        assertThat(returnedVacation).isNotNull();
        assertThat(returnedVacation.getDuration()).isEqualTo(5);
        assertThat(returnedVacation.getVacationStatus()).isEqualTo("APPROVED");
    }

    @Test
    public void changeVacationStatusTest() {
        VacationDTO vacationDTO = vacationService.addVacation(
                employee.getId(),
                "21/02/2021",
                "26/02/2021",
                "REJECTED"
        );

        assertThat(vacationDTO.getVacationStatus())
                .isEqualTo("REJECTED");

        vacationService.changeVacationStatus(
                vacationDTO.getId(),
                employee.getId(),
                VacationStatus.APPROVED
        );

        VacationDTO vacationWithUpdatedStatus = vacationService.findVacation(
                employee.getId(),
                vacationDTO.getId()
        );

        assertThat(vacationWithUpdatedStatus.getVacationStatus())
                .isEqualTo("APPROVED");
    }

    @Test
    public void getVacationsTest() {
        vacationService.addVacation(
                employee.getId(),
                "21/02/2021",
                "26/02/2021",
                "SUBMITTED"
        );
        vacationService.addVacation(
                employee.getId(),
                "21/03/2021",
                "23/03/2021",
                "REJECTED"
        );

        List<VacationDTO> vacations = vacationService.getVacations(
                employee.getId()
        );
        assertThat(vacations).isNotNull();
        assertThat(vacations.size()).isEqualTo(2);
    }

    @Test
    public void getVacationWhenVacationIsNotFound() {
        try {
            vacationService.findVacation(employee.getId(), 3);
        } catch (ApplicationException e) {
            assertThat(e)
                    .isInstanceOf(VacationNotFoundException.class)
                    .hasMessage("Could not find vacation with id: 3 " +
                            "for the employee with id: "
                            + employee.getId());
        }

    }

}
