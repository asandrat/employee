package com.bamboo.employee.controller;

import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.addEmployee(
                employee.getName(),
                employee.getSurname()
        );
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployee(@PathVariable String id) {
        EmployeeDTO employee =  employeeService.getEmployee(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id: " + id
            );
        }
        return employee;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        employeeService.removeEmployee(id);
        return ResponseEntity.ok(
                "Vacation with id " + id + "is successfully deleted"
        );
    }

}
