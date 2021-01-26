package com.bamboo.employee.controller;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.ServerResponse;
import com.bamboo.employee.service.EmployeeService;
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
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createEmployee(
            @Valid
            @RequestBody EmployeeDTO employee
    ) {
        return employeeService.addEmployee(
                employee.getName(),
                employee.getSurname()
        );
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployee(@PathVariable String id) {
        return employeeService.getEmployee(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") String id) {
        employeeService.removeEmployee(id);
        return new ServerResponse(
                "Employee with id " + id + " is successfully deleted"
        ).getMessage();
    }

}
