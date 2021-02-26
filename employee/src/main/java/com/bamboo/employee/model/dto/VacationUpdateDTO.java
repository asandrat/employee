package com.bamboo.employee.model.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class VacationUpdateDTO {

    @NotNull
    String uniqueId;

    @NotNull
    String status;
}
