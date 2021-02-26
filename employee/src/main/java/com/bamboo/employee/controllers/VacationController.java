package com.bamboo.employee.controllers;

import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.model.VacationStatusDTO;
import com.bamboo.employee.service.vacation.VacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/vacations")
public class VacationController {

    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Operation(summary = "Get all vacations")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all vacations",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =
                                    VacationDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vacations not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Collection<VacationDTO>> getAllVacations() {
        return ResponseEntity.ok(vacationService.findAll());
    }

    @Operation(summary = "Add new vacation to employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "New vacation added to employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =
                                    VacationDTO.class))
                    })
    })
    @PostMapping
    public ResponseEntity<VacationDTO> addVacationToEmployee(
            @Valid @RequestBody VacationDTO vacationDTO) {
        String employeeId = vacationDTO.getEmployeeId();
        String dateFrom = vacationDTO.getDateFrom();
        String dateTo = vacationDTO.getDateTo();
        String status = vacationDTO.getStatus();
        return ResponseEntity.ok(vacationService.addVacation(employeeId,
                dateFrom, dateTo, status));
    }

    @Operation(summary = "Delete vacation by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Vacation with provided id deleted",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vacation not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVacationById(@PathVariable String id) {
        vacationService.removeVacation(id);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Update vacation status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Vacation status updated",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Vacation not found",
                    content = @Content)
    })
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
