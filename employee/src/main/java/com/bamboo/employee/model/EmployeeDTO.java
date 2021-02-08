package com.bamboo.employee.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EmployeeDTO {

    private int id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must contains at least 3 letter")
    @Pattern(regexp = "[A-Z][A-Za-z]+")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 3, message = "Surname must contains at least 3 letter")
    @Pattern(regexp = "[A-Z][A-Za-z]+")
    private String surname;

}
