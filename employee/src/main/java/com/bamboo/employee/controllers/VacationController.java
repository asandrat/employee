package com.bamboo.employee.controllers;

import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.service.vacation.VacationService;
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
        return vacationService.addVacation(employeeId, dateFrom, dateTo, status);
    }

    @DeleteMapping("{id}")
    public void deleteVacationById(@PathVariable String id) {
        vacationService.removeVacation(id);
    }

    @PutMapping("{id}/approve")
    public void approveVacation(@PathVariable String id){
        vacationService.approveVacation(id);
    }

    @PutMapping("{id}/reject")
    public void rejectVacation(@PathVariable String id){
        vacationService.rejectVacation(id);
    }


}
