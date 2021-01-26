package com.bamboo.employee.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EmployeeDTO {

    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must contains at least 3 letter")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 3, message = "Surname must contains at least 3 letter")
    private String surname;

}
