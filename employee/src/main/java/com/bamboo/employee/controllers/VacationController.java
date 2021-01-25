package com.bamboo.employee.controllers;

import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.service.vacation.VacationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacations")
public class VacationController {

    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    public List<VacationDTO> getAllVacations() {
        return vacationService.findAll();
    }

    @PostMapping
    public VacationDTO addVacationToEmployee(@RequestBody VacationDTO vacationDTO) {
        String employeeId = vacationDTO.getEmployeeId();
        String dateFrom = vacationDTO.getDateFrom().toString();
        String dateTo = vacationDTO.getDateTo().toString();
        String status = vacationDTO.getStatus().toString();
        return vacationService.addVacation(employeeId, dateFrom, dateTo,
                status);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVacationById(@PathVariable String id) {
        if (vacationService.removeVacation(id)) {
            return new ResponseEntity<>("" +
                    "vacation with id: " + id + " deleted.",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                "vacation not found.",
                HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}/approve")
    public ResponseEntity<String> approveVacation(@PathVariable String id) {
        vacationService.approveVacation(id);
        return new ResponseEntity<>(
                "vacation with id: " + id + " approved.",
                HttpStatus.OK);
    }

    @PutMapping("{id}/reject")
    public ResponseEntity<String> rejectVacation(@PathVariable String id) {
        vacationService.rejectVacation(id);
        return new ResponseEntity<>(
                "vacation with id: " + id + " rejected.",
                HttpStatus.OK);
    }


}
