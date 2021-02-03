package com.bamboo.employee.controller;


import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.EmployeeEntity;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.model.dto.EmployeeDTO;
import com.bamboo.employee.model.dto.VacationDTO;
import com.bamboo.employee.model.dto.VacationStatusDTO;
import com.bamboo.employee.service.employee.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

/*
Dataflow
    dto -> controler -> domain object -> service -> entity -> repo
    dto <- controler <- domain object <- service <- entity <- repo
 */

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final ModelMapper modelMapper;

    public EmployeeController(final EmployeeService service,
                              final ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    ResponseEntity<EmployeeDTO> addEmployee(
            @RequestBody @Valid final EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        return ResponseEntity.ok(modelMapper.map(service.addEmployee(employee),
                EmployeeDTO.class));
    }

    @GetMapping
    ResponseEntity<Collection<EmployeeDTO>> allEmployees() {
        Collection<EmployeeDTO> employees = service.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable final int id) {
        Employee employee = service.getEmployee(id);
        return ResponseEntity.ok(modelMapper.map(employee, EmployeeDTO.class));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EmployeeDTO> removeEmployeeById(@PathVariable final int id) {
        return ResponseEntity.ok(
                modelMapper.map(service.removeEmployee(id), EmployeeDTO.class));
    }

    @PostMapping("/{employeeId}/vacations/")
    ResponseEntity<VacationDTO> addVacationToEmployee(
            @PathVariable final int employeeId,
            @RequestBody @Valid final VacationDTO vacationDTO,
            HttpServletRequest request) {

        Vacation vacation = modelMapper.map(vacationDTO, Vacation.class);
        Vacation responseBody = service.addVacationToEmployee(employeeId,
                vacation);
        return ResponseEntity.ok(modelMapper.map(responseBody,
                VacationDTO.class));
    }

    @GetMapping("/{employeeId}/vacations/{vacationId}")
    ResponseEntity<VacationDTO> getVacationById(@PathVariable int employeeId,
                                                @PathVariable int vacationId) {
        Vacation vacation = service.getVacationFromEmployee(employeeId, vacationId);
        return ResponseEntity.ok(modelMapper.map(vacation, VacationDTO.class));
    }

    @DeleteMapping("/{employeeId}/vacations/{vacationId}")
    ResponseEntity<String> removeVacationById(
            @PathVariable int employeeId,
            @PathVariable int vacationId) {

        int numberOfEntriesDeleted =
                service.removeVacationFromEmployee(employeeId, vacationId);
        return ResponseEntity.ok("Number of vacations deleted " + numberOfEntriesDeleted);
    }

    @GetMapping("/{employeeId}/vacations")
    ResponseEntity<Collection<VacationDTO>> getAllVacationsOfEmployee(@PathVariable final int employeeId) {
        Collection<VacationDTO> vacations =
                service.findAllEmployeesVacations(employeeId)
                        .stream()
                        .map(vacation -> modelMapper.map(vacation,
                                VacationDTO.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(vacations);
    }

    @PatchMapping("/{employeeId}/vacations/")
    ResponseEntity<Integer> updateVacationOfEmployee(
            @PathVariable final int employeeId,
            @RequestBody final VacationStatusDTO statusDTO) {

        int vacationId = Integer.parseInt(statusDTO.getUniqueId());
        VacationStatus status = VacationStatus.valueOf(statusDTO.getStatus());
        return ResponseEntity.ok(
                service.updateVacationForEmployee(employeeId,
                        vacationId,
                        status));
    }
}
