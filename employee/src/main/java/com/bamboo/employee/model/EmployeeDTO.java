package com.bamboo.employee.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmployeeDTO {
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    private String registrationDate;

}
