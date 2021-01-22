package com.bamboo.employee.controller;

import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.custom.exception.VacationNotFoundException;
import com.bamboo.employee.entities.Employee;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class VacationController {

    private final EmployeeService employeeService;

    public VacationController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}/vacations")
    public List<VacationDTO> getAllVacations(@PathVariable String employeeId) {

        Employee employee = employeeService.findEmployee(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id " + employeeId
            );
        }
        return employeeService.getVacations(employeeId);
    }

    @GetMapping("{employeeId}/vacations/{vacationId}")
    public VacationDTO getVacation(
            @PathVariable String employeeId,
            @PathVariable String vacationId
    ) {
        EmployeeDTO employee = employeeService.getEmployee(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id " + employeeId
            );
        }
        VacationDTO vacation = employeeService.findVacation(employeeId, vacationId);
        if (vacation == null) {
            throw new VacationNotFoundException(
                    "Could not vacation with id " + vacationId
            );
        }
        return vacation;
    }

    @PostMapping("/{employeeId}/vacations")
    public VacationDTO createVacation(
            @PathVariable String employeeId,
            @RequestBody VacationDTO vacation) {
        EmployeeDTO employee = employeeService.getEmployee(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id " + employeeId
            );
        }
        return employeeService.addVacation(
                employeeId,
                vacation.getDateFrom(),
                vacation.getDateTo(),
                vacation.getVacationStatus()
        );
    }

    @PatchMapping("/{employeeId}/vacations/{vacationId}/approve")
    public ResponseEntity<?> approveVacationStatus(
            @PathVariable String employeeId,
            @PathVariable String vacationId
    ) {
        EmployeeDTO employee = employeeService.getEmployee(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id " + employeeId
            );
        }
        employeeService.approveVacation(vacationId, employeeId);
        return ResponseEntity.ok("Vacation status is updated");
    }

    @PatchMapping("/{employeeId}/vacations/{vacationId}/reject")
    public ResponseEntity<?> rejectVacationStatus(
            @PathVariable String employeeId,
            @PathVariable String vacationId
    ) {
        EmployeeDTO employee = employeeService.getEmployee(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id " + employeeId
            );
        }
        employeeService.rejectVacation(vacationId, employeeId);
        return ResponseEntity.ok("Vacation status is updated");
    }


    @DeleteMapping("{employeeId}/vacations/{vacationId}")
    public ResponseEntity<?>  deleteVacation(
            @PathVariable String employeeId,
            @PathVariable String vacationId) {
        employeeService.removeVacation(vacationId, employeeId);
        return ResponseEntity.ok(
                "Vacation with id " + vacationId + "is successfully deleted"
        );
    }
}
