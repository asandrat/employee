package com.bamboo.employee.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class VacationDTO {

    private String id;

    @NotBlank(message = "Date from is required")
    @Pattern(regexp = "([0-9]{2})\\([0-9]{2}\\([0-9]{4}")
    private String dateFrom;

    @NotBlank(message = "Date to is required")
    @Pattern(regexp = "([0-9]{2})\\([0-9]{2}\\([0-9]{4}")
    private String dateTo;

    @Positive
    private long duration;

    @NotBlank(message = "Vacation status is required")
    private String vacationStatus;

}
