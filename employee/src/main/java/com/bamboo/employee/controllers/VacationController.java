package com.bamboo.employee.controllers;

import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.model.VacationStatusDTO;
import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vacations")
public class VacationController {

    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    public ResponseEntity<List<VacationDTO>> getAllVacations() {
        return ResponseEntity.ok(vacationService.findAll());
    }

    @PostMapping
    public ResponseEntity<VacationDTO> addVacationToEmployee(@Valid @RequestBody VacationDTO vacationDTO) {
        String employeeId = vacationDTO.getEmployeeId();
        String dateFrom = vacationDTO.getDateFrom();
        String dateTo = vacationDTO.getDateTo();
        String status = vacationDTO.getStatus();
        return ResponseEntity.ok(vacationService.addVacation(employeeId,
                dateFrom, dateTo, status));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVacationById(@PathVariable String id) {
        if (vacationService.removeVacation(id).isPresent()) {
            return ResponseEntity.ok(id);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<String> changeVacationStatus(
            @Valid @RequestBody VacationStatusDTO vacationStatusDTO) {
        String id = vacationStatusDTO.getVacationId();
        String status = vacationStatusDTO.getStatus();
        if (!status.equalsIgnoreCase("approved")
                && !status.equalsIgnoreCase("rejected")) {
            return ResponseEntity.badRequest().build();
        }
        if (status.equalsIgnoreCase("approved")) {
            vacationService.approveVacation(id);
        } else {
            vacationService.rejectVacation(id);
        }
        return ResponseEntity.ok(id);
    }

}
