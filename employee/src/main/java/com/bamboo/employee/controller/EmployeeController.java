package com.bamboo.employee.controller;


import com.bamboo.employee.model.Employee;
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
        Employee e = new Employee(
                Integer.parseInt(employeeDTO.getUniqueId()),
                employeeDTO.getName(),
                employeeDTO.getSurname());

        return ResponseEntity.ok(modelMapper.map(service.addEmployee(e), EmployeeDTO.class));
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

        // todo videti kako direktno mapovati sa modelMapperom
        Vacation vacation = new Vacation(employeeId,
                Integer.parseInt(vacationDTO.getUniqueId()),
                LocalDate.parse(vacationDTO.getFrom()),
                LocalDate.parse(vacationDTO.getTo()),
                vacationDTO.getDuration(),
                VacationStatus.valueOf(vacationDTO.getStatus()));

        Vacation v = service.addVacationToEmployee(vacation);
        System.out.println(v);
        return ResponseEntity.ok(modelMapper.map(v, VacationDTO.class));
    }

    @GetMapping("/{employeeId}/vacations/{id}")
    ResponseEntity<VacationDTO> getVacationById(@PathVariable int employeeId,
                                      @PathVariable int vacationId) {
        VacationId id = new VacationId(employeeId, vacationId);
        Vacation vacation = service.getVacationFromEmployee(id);
        return ResponseEntity.ok(modelMapper.map(vacation, VacationDTO.class));
    }

    @DeleteMapping("/{employeeId}/vacations/{id}")
    ResponseEntity<VacationDTO> removeVacationById(
            @PathVariable int employeeId,
            @PathVariable int vacationId) {

        VacationId id = new VacationId(employeeId, vacationId);
        Vacation vacation = service.removeVacationFromEmployee(id);
        return ResponseEntity.ok(modelMapper.map(vacation, VacationDTO.class));
    }

    @GetMapping("/{employeeId}/vacations")
    ResponseEntity<Collection<VacationDTO>> getAllVacationsOfEmployee(@PathVariable final int employeeId) {
        Collection<VacationDTO> vacations = service.getEmployee(employeeId)
                .getVacations().stream()
                .map(vacation -> modelMapper.map(vacation, VacationDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(vacations);
    }

    @PatchMapping("/{employeeId}/vacations/")
    ResponseEntity<VacationDTO> updateVacationOfEmployee(
            @PathVariable final int employeeId,
            @RequestBody final VacationStatusDTO statusDTO) {

        VacationId id = new VacationId(
                employeeId,
                Integer.parseInt(statusDTO.getUniqueId()));
        VacationStatus status = VacationStatus.valueOf(statusDTO.getStatus());
        Vacation v = service.updateVacationForEmployee(id, status);
        return ResponseEntity.ok(modelMapper.map(v, VacationDTO.class));
    }
}
