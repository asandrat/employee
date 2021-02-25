package com.bamboo.employee.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VacationStatusDTO {
    private String vacationId;

    @NotBlank(message = "status is required")
    private String status;
}
