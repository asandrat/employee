package com.bamboo.employee.controller;


import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.model.dto.EmployeeDTO;
import com.bamboo.employee.model.dto.VacationDTO;
import com.bamboo.employee.model.dto.VacationUpdateDTO;
import com.bamboo.employee.service.employee.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final ModelMapper modelMapper;

    public EmployeeController(final EmployeeService service,
                              final ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    ResponseEntity<EmployeeDTO> addEmployee(
            @RequestBody @Valid final EmployeeDTO employeeDTO) {
        Employee inputEmployee = modelMapper.map(employeeDTO, Employee.class);
        Employee outputEmployee = service.addEmployee(inputEmployee);
        return ResponseEntity.ok(modelMapper.map(outputEmployee, EmployeeDTO.class));
    }

    @GetMapping
    ResponseEntity<Collection<EmployeeDTO>> allEmployees() {
        Collection<EmployeeDTO> employees =
                service.findAll().stream()
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
        Employee employee = service.removeEmployee(id);
        return ResponseEntity.ok(modelMapper.map(employee, EmployeeDTO.class));
    }

    @PostMapping("/{employeeId}/vacations/")
    ResponseEntity<VacationDTO> addVacationToEmployee(
            @PathVariable final int employeeId,
            @RequestBody @Valid final VacationDTO vacationDTO,
            HttpServletRequest request) {

        Vacation vacation = modelMapper.map(vacationDTO, Vacation.class);
        Vacation outputVacation = service.addVacationToEmployee(employeeId, vacation);
        return ResponseEntity.ok(modelMapper.map(outputVacation, VacationDTO.class));
    }

    @GetMapping("/{employeeId}/vacations/{vacationId}")
    ResponseEntity<VacationDTO> getVacationById(@PathVariable int employeeId,
                                                @PathVariable int vacationId) {
        Vacation vacation = service.getVacationFromEmployee(employeeId, vacationId);
        return ResponseEntity.ok(modelMapper.map(vacation, VacationDTO.class));
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
                        .map(vacation -> modelMapper.map(vacation,
                                VacationDTO.class))
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
