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
import java.net.URI;
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

    EmployeeController(final EmployeeService service) {
        this.service = service;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    ResponseEntity<?> addEmployee(@RequestBody final EmployeeDTO employeeDTO,
                                  HttpServletRequest request) {
        Employee e = new Employee(
                Integer.parseInt(employeeDTO.getUniqueId()),
                employeeDTO.getName(),
                employeeDTO.getSurname());

        if (service.addEmployee(e)) {
            return ResponseEntity
                    .created(URI.create(request.getRequestURI()))
                    .body(employeeDTO);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(employeeDTO);
    }

    @GetMapping
    ResponseEntity<?> allEmployees() {
         Collection<EmployeeDTO> employees = service.findAll().stream()
                 .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                 .collect(Collectors.toList());
         return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getEmployeeById(@PathVariable final int id) {
        Employee employee = service.getEmployee(id);
        return ResponseEntity.ok(modelMapper.map(employee, EmployeeDTO.class));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> removeEmployeeById(@PathVariable final int id) {
        return ResponseEntity.ok(modelMapper.map(
                service.removeEmployee(id),
                EmployeeDTO.class));
    }

    @PostMapping("/{id}/vacations/")
    ResponseEntity<?> addVacationToEmployee(
            @PathVariable final int id,
            @RequestBody final VacationDTO vacationDTO,
            HttpServletRequest request) {

        Vacation v = new Vacation(id,
                Integer.parseInt(vacationDTO.getUniqueId()),
                LocalDate.parse(vacationDTO.getFrom()),
                LocalDate.parse(vacationDTO.getTo()),
                vacationDTO.getDuration(),
                VacationStatus.valueOf(vacationDTO.getStatus()));

        service.addVacationToEmployee(v);
        return ResponseEntity.created(URI.create(request.getRequestURI()))
                .body(vacationDTO);
    }

    @GetMapping("/{employeeId}/vacations/{id}")
    ResponseEntity<?> getVacationById(@PathVariable int employeeId,
                                                @PathVariable int id) {
        VacationId vacationId = new VacationId(employeeId, id);
        Vacation v = service.getVacationFromEmployee(vacationId);
        return ResponseEntity.ok(modelMapper.map(v, VacationDTO.class));
    }

    @DeleteMapping("/{employeeId}/vacations/{id}")
    ResponseEntity<?> removeVacationById(
            @PathVariable int employeeId,
            @PathVariable int id) {

        VacationId vacationId = new VacationId(employeeId, id);
        Vacation v = service.removeVacationFromEmployee(vacationId);
        return ResponseEntity.ok(modelMapper.map(v, VacationDTO.class));
    }

    @GetMapping("/{id}/vacations")
    ResponseEntity<?> allVacationsOfEmployee(@PathVariable final int id) {
        Collection<VacationDTO> vacations =  service.getEmployee(id)
                .getVacations().stream()
                .map(vacation -> modelMapper.map(vacation, VacationDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(vacations);
    }

    @PatchMapping("/{employeeId}/vacations/")
    void updateVacationOfEmployee(@PathVariable final int employeeId,
                                    @RequestBody final VacationStatusDTO statusDTO) {
        int id = Integer.parseInt(statusDTO.getUniqueId());
        if (statusDTO.getStatus().equals("approve")) {
            service.approveVacationForEmployee(new VacationId(employeeId, id));
        } else if (statusDTO.getStatus().equals("reject")) {
            service.rejectVacationForEmployee(new VacationId(employeeId, id));
        }
        // todo
        // should throw -> bad client input
    }

}
