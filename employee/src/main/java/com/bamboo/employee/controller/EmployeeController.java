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
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(final EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<EmployeeDTO> addEmployee(
            @RequestBody @Valid final EmployeeDTO employeeDTO) {
        Employee inputEmployee =
                EmployeeMapper.INSTANCE.EmployeeDTOtoEmployee(employeeDTO);
        Employee outputEmployee = service.addEmployee(inputEmployee);
        return ResponseEntity.ok(EmployeeMapper.INSTANCE.employeeToDTO(outputEmployee));
    }

    @GetMapping
    ResponseEntity<Collection<EmployeeDTO>> allEmployees() {
        Collection<EmployeeDTO> employees =
                service.findAll().stream()
                        .map(EmployeeMapper.INSTANCE::employeeToDTO)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable final int id) {
        Employee employee = service.getEmployee(id);
        return ResponseEntity.ok(EmployeeMapper.INSTANCE.employeeToDTO(employee));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EmployeeDTO> removeEmployeeById(@PathVariable final int id) {
        Employee employee = service.removeEmployee(id);
        return ResponseEntity.ok(EmployeeMapper.INSTANCE.employeeToDTO(employee));
    }

    @PostMapping("/{employeeId}/vacations/")
    ResponseEntity<VacationDTO> addVacationToEmployee(
            @PathVariable final int employeeId,
            @RequestBody @Valid final VacationDTO vacationDTO) {

        Vacation vacation =
                VacationMapper.INSTANCE.vacationDTOtoVacation(vacationDTO);
        Vacation outputVacation = service.addVacationToEmployee(employeeId,
                vacation);
        return ResponseEntity.ok(VacationMapper.INSTANCE.vacationToDTO(outputVacation));
    }

    @GetMapping("/{employeeId}/vacations/{vacationId}")
    ResponseEntity<VacationDTO> getVacationById(@PathVariable int employeeId,
                                                @PathVariable int vacationId) {
        Vacation vacation = service.getVacationFromEmployee(employeeId,
                vacationId);
        return ResponseEntity.ok(VacationMapper.INSTANCE.vacationToDTO(vacation));
    }

    @DeleteMapping("/{employeeId}/vacations/{vacationId}")
    ResponseEntity<Void> removeVacationById(
            @PathVariable int employeeId,
            @PathVariable int vacationId) {
        service.removeVacationFromEmployee(employeeId, vacationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{employeeId}/vacations")
    ResponseEntity<Collection<VacationDTO>> getAllVacationsOfEmployee(@PathVariable final int employeeId) {
        Collection<VacationDTO> vacations =
                service.findAllEmployeesVacations(employeeId)
                        .stream()
                        .map(VacationMapper.INSTANCE::vacationToDTO)
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
