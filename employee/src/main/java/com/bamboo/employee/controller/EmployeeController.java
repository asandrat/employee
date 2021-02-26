package com.bamboo.employee.controller;


import com.bamboo.employee.mapper.EmployeeMapper;
import com.bamboo.employee.mapper.VacationMapper;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.model.dto.EmployeeDTO;
import com.bamboo.employee.model.dto.VacationDTO;
import com.bamboo.employee.model.dto.VacationUpdateDTO;
import com.bamboo.employee.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/*
Dataflow
    dto -> controler -> domain object -> service -> entity -> repo
    dto <- controler <- domain object <- service <- entity <- repo
 */

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeMapper employeeMapper;
    private final VacationMapper vacationMapper;

    @Operation(summary = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successfully created employee",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation =
                                    EmployeeDTO.class)),
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "bad request body",
                    content = @Content
            )
    })
    @PostMapping
    ResponseEntity<EmployeeDTO> addEmployee(
            @RequestBody @Valid final EmployeeDTO employeeDTO) {
        Employee inputEmployee =
                employeeMapper.EmployeeDTOtoEmployee(employeeDTO);
        Employee outputEmployee = service.addEmployee(inputEmployee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeMapper.employeeToDTO(outputEmployee));
    }

    @GetMapping
    ResponseEntity<Collection<EmployeeDTO>> allEmployees() {
        Collection<EmployeeDTO> employees =
                service.findAllEmployees().stream()
                        .map(employeeMapper::employeeToDTO)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get employee by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "found employee for given id",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation =
                                    EmployeeDTO.class)),
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "employee doesn't exist with given id",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<EmployeeDTO> getEmployeeById(
            @Parameter(description = "Id of the employee to be searched")
            @PathVariable final int id) {
        Employee employee = service.getEmployee(id);
        return ResponseEntity.ok(employeeMapper.employeeToDTO(employee));
    }

    @Operation(summary = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "successfully deleted employee for given id",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "employee with given id doesn't exists",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> removeEmployeeById(
            @Parameter(description = "Id of the employee to be deleted")
            @PathVariable final int id) {
        service.removeEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new vacation for employee")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successfully created vacation",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation =
                                    VacationDTO.class)),
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "bad request body",
                    content = @Content
            )
    })
    @PostMapping("/{employeeId}/vacations/")
    ResponseEntity<VacationDTO> addVacationToEmployee(
            @PathVariable int employeeId,
            @RequestBody @Valid final VacationDTO vacationDTO) {

        Vacation vacation =
                vacationMapper.vacationDTOtoVacation(vacationDTO);
        Vacation outputVacation = service.addVacationToEmployee(employeeId,
                vacation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vacationMapper.vacationToDTO(outputVacation));
    }

    @Operation(summary = "Get vacation by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "found vacation for given id",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation =
                                    VacationDTO.class)),
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "vacation doesn't exist with given id",
                    content = @Content
            )
    })
    @GetMapping("/{employeeId}/vacations/{vacationId}")
    ResponseEntity<VacationDTO> getVacationById(@PathVariable int employeeId,
                                                @PathVariable int vacationId) {
        Vacation vacation = service.getVacationFromEmployee(employeeId,
                vacationId);
        return ResponseEntity.ok(vacationMapper.vacationToDTO(vacation));
    }


    @Operation(summary = "Delete vacation by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "successfully deleted vacation for given id",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "vacation with given id doesn't exists",
                    content = @Content
            )
    })
    @DeleteMapping("/{employeeId}/vacations/{vacationId}")
    ResponseEntity<Void> removeVacationById(
            @PathVariable int employeeId,
            @PathVariable int vacationId) {
        service.removeVacationFromEmployee(employeeId, vacationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}/vacations")
    ResponseEntity<Collection<VacationDTO>> getAllVacationsOfEmployee(@PathVariable final int employeeId) {
        Collection<VacationDTO> vacations =
                service.findAllEmployeesVacations(employeeId)
                        .stream()
                        .map(vacationMapper::vacationToDTO)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(vacations);
    }

    @PatchMapping("/{employeeId}/vacations/")
    ResponseEntity<Void> updateVacationOfEmployee(
            @PathVariable final int employeeId,
            @RequestBody final VacationUpdateDTO statusDTO) {

        int vacationId = Integer.parseInt(statusDTO.getUniqueId());
        VacationStatus status = VacationStatus.valueOf(statusDTO.getStatus());
        service.updateVacationForEmployee(employeeId, vacationId, status);
        return ResponseEntity.ok().build();
    }
}
