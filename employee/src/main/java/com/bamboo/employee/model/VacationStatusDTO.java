package com.bamboo.employee.model;

import javax.validation.constraints.NotBlank;

public class VacationStatusDTO {

    private String vacationId;
    @NotBlank(message = "status is required")
    private String status;

    public VacationStatusDTO() {
    }

    public VacationStatusDTO(String vacationId, String status) {
        this.vacationId = vacationId;
        this.status = status;
    }

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
