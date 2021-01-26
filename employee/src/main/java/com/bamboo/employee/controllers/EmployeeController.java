package com.bamboo.employee.controllers;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.service.employee.EmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addNewEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        String name = employeeDTO.getName();
        String surname = employeeDTO.getSurname();

        return ResponseEntity.ok(employeeService.addEmployee(name, surname));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable String id) {
        if (employeeService.removeEmployee(id).isPresent()) {
            return ResponseEntity.ok("employee with id: " + id + " deleted.");
        }
        return ResponseEntity.notFound().build();
    }
}
