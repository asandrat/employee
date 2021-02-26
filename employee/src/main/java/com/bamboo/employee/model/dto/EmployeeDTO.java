package com.bamboo.employee.model.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class EmployeeDTO {

    String uniqueId;

    @NotNull
    @Size(min=2, max=80)
    String name;

    @NotNull
    @Size(min=2, max=80)
    String surname;
}
