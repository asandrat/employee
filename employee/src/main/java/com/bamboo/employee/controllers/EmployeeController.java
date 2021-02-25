package com.bamboo.employee.controllers;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.service.employee.EmployeeService;

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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all employees")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =
                                    EmployeeDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employees not found",
                    content = @Content)

    })
    @GetMapping
    public ResponseEntity<Collection<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Operation(summary = "Add new employee")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New employee added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =
                                    EmployeeDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Name,surname and registration date are " +
                            "required fields",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<EmployeeDTO> addNewEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        String name = employeeDTO.getName();
        String surname = employeeDTO.getSurname();
        String registrationDate = employeeDTO.getRegistrationDate();
        return ResponseEntity.ok(employeeService.addEmployee(name, surname,
                registrationDate));
    }

    @Operation(summary = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee with provided id deleted",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        employeeService.removeEmployee(id);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Get all vacations of employee")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all vacations of employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation =
                                    VacationDTO.class))
                    })
    })
    @GetMapping("/{id}/vacations")
    public ResponseEntity<Collection<VacationDTO>> getAllVacationsOfEmployee(
            @PathVariable String id) {
        Collection<VacationDTO> vacations =
                employeeService.findAllVacationsOfEmployee(id);
        return ResponseEntity.ok(vacations);
    }

}
