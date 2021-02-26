package com.bamboo.employee.controller;

import com.bamboo.employee.entity.VacationStatus;
import com.bamboo.employee.model.ServerResponse;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.model.VacationStatusDTO;
import com.bamboo.employee.service.VacationService;
import com.bamboo.employee.validator.VacationStatusValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees/{employeeId}")
public class VacationController {

    private final VacationService vacationService;


    @Operation(summary = "Get all vacations for employee with provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found all vacations for provided employee id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VacationDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid employee id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Vacations not found for provided employee id",
                    content = @Content)
    })
    @GetMapping("/vacations")
    public List<VacationDTO> getAllVacations(@PathVariable int employeeId) {
        return vacationService.getVacations(employeeId);
    }

    @Operation(summary = "Get specific vacation for specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the vacation for certain employee",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VacationDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid vacation id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Vacation not found for provided employee id",
                    content = @Content)
    })
    @GetMapping("/vacations/{vacationId}")
    public VacationDTO getVacation(
            @PathVariable int employeeId,
            @PathVariable int vacationId
    ) {
        return vacationService.findVacation(employeeId, vacationId);
    }

    @Operation(summary = "Create new vacation for specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "New vacation for specific employee created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VacationDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid employee id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Employee with provided id not found",
                    content = @Content)
    })
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

    @Operation(summary = "Update specific vacation for specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The vacation for certain employee updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServerResponse.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid vacation id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Vacation not found for provided employee id",
                    content = @Content)
    })
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

    @Operation(summary = "Delete specific vacation for specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The vacation for certain employee deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServerResponse.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid vacation id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Vacation not found for provided employee id",
                    content = @Content)
    })
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
