package com.bamboo.employee.model.dto;

import lombok.Value;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class VacationDTO {

    @Nullable
    String id;

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String from;

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String to;

    @Nullable
    String duration;

    @NotNull
    String status;
}
