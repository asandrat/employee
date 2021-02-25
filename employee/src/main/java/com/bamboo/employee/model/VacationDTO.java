package com.bamboo.employee.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class VacationDTO {
    private String id;

    @NotBlank(message = "employeeId is required")
    private String employeeId;

    @NotBlank(message = "dateFrom is required")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String dateFrom;

    @NotBlank(message = "dateTo is required")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String dateTo;

    private int duration;

    @NotBlank(message = "status is required")
    private String status;

}
