package com.bamboo.employee.controller;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.ServerResponse;
import com.bamboo.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found all employees",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))
            }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Employees not found",
                    content = @Content)
    })
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @Operation(summary = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "New employee created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))
            }),
            @ApiResponse(responseCode = "400",
                    description = "Name and surname are required fields",
                    content = @Content)
    })
    @PostMapping
    public EmployeeDTO createEmployee(
            @Valid
            @RequestBody EmployeeDTO employee
    ) {
        return employeeService.addEmployee(
                employee.getName(),
                employee.getSurname()
        );
    }

    @Operation(summary = "Get the employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the employee with provided id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))
            }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Employee not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public EmployeeDTO getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @Operation(summary = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Employee with provided id deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServerResponse.class))
            }),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Employee not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ServerResponse deleteEmployeeById(@PathVariable("id") int id) {
        employeeService.removeEmployee(id);
        return new ServerResponse(
                "Employee with id: " + id + " is successfully deleted"
        );
    }
}
