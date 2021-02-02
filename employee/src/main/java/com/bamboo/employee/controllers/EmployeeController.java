package com.bamboo.employee.controllers;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.service.employee.EmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Collection<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addNewEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        String name = employeeDTO.getName();
        String surname = employeeDTO.getSurname();
        return ResponseEntity.ok(employeeService.addEmployee(name, surname));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        employeeService.removeEmployee(id);
        return ResponseEntity.ok(id);
    }
}
