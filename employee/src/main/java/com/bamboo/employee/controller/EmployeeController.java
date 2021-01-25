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

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

/*
Dataflow
    dto -> controler -> entity -> service -> ...
    dto <- controler <- entity <- service <- ...
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
    ResponseEntity<?> addEmployee(@RequestBody final EmployeeDTO employeeDTO) {
        Employee e = new Employee(
                Integer.parseInt(employeeDTO.getUniqueId()),
                employeeDTO.getName(),
                employeeDTO.getSurname());

        if (service.addEmployee(e)) {
            return new ResponseEntity<>(
                    "Successfully added employee with id " + employeeDTO.getUniqueId(),
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>(
                "Employee with id " + employeeDTO.getUniqueId() + " already exists",
                HttpStatus.CONFLICT);
    }

    @GetMapping
    Collection<EmployeeDTO> allEmployees() {
         return service.findAll().stream()
                 .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                 .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    EmployeeDTO getEmployeeById(@PathVariable final int id) {
        Employee employee = service.getEmployee(id);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> removeEmployeeById(@PathVariable final int id) {
        service.removeEmployee(id);
        return new ResponseEntity<>(
                "Successfully deleted employee with id: " + id,
                HttpStatus.OK);
    }

    @PostMapping("/{id}/vacations/")
    ResponseEntity<String> addVacationToEmployee(
            @PathVariable final int id,
            @RequestBody final VacationDTO vacationDTO) {

        Vacation v = new Vacation(id,
                Integer.parseInt(vacationDTO.getUniqueId()),
                LocalDate.parse(vacationDTO.getFrom()),
                LocalDate.parse(vacationDTO.getTo()),
                vacationDTO.getDuration(),
                VacationStatus.valueOf(vacationDTO.getStatus()));

        service.addVacationToEmployee(v);
        return new ResponseEntity<>(
                "Successfully added vacation with id: "
                        + vacationDTO.getUniqueId()
                        + " to employee with id " + id,
                HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/vacations/{id}")
    VacationDTO getVacationById(@PathVariable int employeeId,
                                @PathVariable int id) {
        VacationId vacationId = new VacationId(employeeId, id);
        Vacation v = service.getVacationFromEmployee(vacationId);
        return modelMapper.map(v, VacationDTO.class);
    }

    @DeleteMapping("/{employeeId}/vacations/{id}")
    ResponseEntity<VacationDTO> removeVacationById(
            @PathVariable int employeeId,
            @PathVariable int id) {

        VacationId vacationId = new VacationId(employeeId, id);
        Vacation v = service.removeVacationFromEmployee(vacationId);
        return new ResponseEntity<>(
                modelMapper.map(v, VacationDTO.class),
                HttpStatus.OK);
    }

    @GetMapping("/{id}/vacations")
    Collection<VacationDTO> allVacationsOfEmployee(@PathVariable final int id) {
        return service.getEmployee(id)
                .getVacations().stream()
                .map(vacation -> modelMapper.map(vacation, VacationDTO.class))
                .collect(Collectors.toList());
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
