package com.bamboo.employee.controller;

import com.bamboo.employee.entity.VacationStatus;
import com.bamboo.employee.model.ServerResponse;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.model.VacationStatusDTO;
import com.bamboo.employee.service.VacationService;
import com.bamboo.employee.validator.VacationStatusValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees/{employeeId}")
public class VacationController {

    private final VacationService vacationService;

    @GetMapping("/vacations")
    public List<VacationDTO> getAllVacations(@PathVariable int employeeId) {
        return vacationService.getVacations(employeeId);
    }

    @GetMapping("/vacations/{vacationId}")
    public VacationDTO getVacation(
            @PathVariable int employeeId,
            @PathVariable int vacationId
    ) {
        return vacationService.findVacation(employeeId, vacationId);
    }

    @PostMapping("/vacations")
    public VacationDTO createVacation(
            @Valid
            @PathVariable int employeeId,
            @RequestBody VacationDTO vacation) {

        return vacationService.addVacation(
                employeeId,
                vacation.getDateFrom(),
                vacation.getDateTo(),
                vacation.getVacationStatus()
        );
    }

    @PatchMapping("/vacations")
    public ServerResponse changeVacationStatus(
            @PathVariable int employeeId,
            @RequestBody VacationStatusDTO vacationStatusDTO
            ) {
        int vacationId = vacationStatusDTO.getVacationId();
        String status = vacationStatusDTO.getVacationStatus();
        VacationStatus vacationStatus= VacationStatusValidator.isValid(status);

        vacationService.changeVacationStatus(
                vacationId,
                employeeId,
                vacationStatus);
        return new ServerResponse(
                "Vacation with id: " + vacationId + " is successfully updated."
        );
    }

    @DeleteMapping("/vacations/{vacationId}")
    public ServerResponse deleteVacation(
            @PathVariable int employeeId,
            @PathVariable int vacationId) {
        vacationService.removeVacation(vacationId, employeeId);
        return new ServerResponse(
                "Vacation with id: " + vacationId + " is successfully deleted"
        );
    }
}
