package com.bamboo.employee.controllers;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.service.employee.EmployeeService;
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
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.findAll();
    }

    @PostMapping
    public EmployeeDTO addNewEmployee(@RequestBody EmployeeDTO employeeDTO){
        String name = employeeDTO.getName();
        String surname = employeeDTO.getSurname();

        return employeeService.addEmployee(name, surname);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable String id) {
        employeeService.removeEmployee(id);
    }

}
