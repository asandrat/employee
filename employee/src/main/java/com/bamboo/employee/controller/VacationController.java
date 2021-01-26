package com.bamboo.employee.controller;

import com.bamboo.employee.model.ServerResponse;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.model.VacationStatusDTO;
import com.bamboo.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees/{employeeId}")
public class VacationController {

    private final EmployeeService employeeService;

    public VacationController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/vacations")
    public List<VacationDTO> getAllVacations(@PathVariable String employeeId) {
        return employeeService.getVacations(employeeId);
    }

    @GetMapping("/vacations/{vacationId}")
    public VacationDTO getVacation(
            @PathVariable String employeeId,
            @PathVariable String vacationId
    ) {
        return employeeService.findVacation(employeeId, vacationId);
    }

    @PostMapping("/vacations")
    public VacationDTO createVacation(
            @Valid
            @PathVariable String employeeId,
            @RequestBody VacationDTO vacation) {

        return employeeService.addVacation(
                employeeId,
                vacation.getDateFrom(),
                vacation.getDateTo(),
                vacation.getVacationStatus()
        );
    }

    @PatchMapping("/vacations")
    public String changeVacationStatus(
            @PathVariable String employeeId,
            @RequestBody VacationStatusDTO vacationStatus
            ) {
        String vacationId = vacationStatus.getVacationId();

        if (vacationStatus.getVacationStatus().equalsIgnoreCase(
                "Approved"
        )) {
            employeeService.approveVacation(vacationId, employeeId);
        } else if (vacationStatus.getVacationStatus().equalsIgnoreCase(
                "Rejected"
        )) {
            employeeService.rejectVacation(vacationId, employeeId);
        } else {
            throw new IllegalArgumentException(
                    "Vacation state is invalid"
            );
        }
        return new ServerResponse(
                "Vacation with id " + vacationId + " is successfully updated."
        ).getMessage();
    }

    @DeleteMapping("/vacations/{vacationId}")
    public String deleteVacation(
            @PathVariable String employeeId,
            @PathVariable String vacationId) {
        employeeService.removeVacation(vacationId, employeeId);
        return new ServerResponse(
                "Vacation with id " + vacationId + " is successfully deleted"
        ).getMessage();
    }
}
