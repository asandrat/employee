package com.bamboo.employee.model;

public class VacationStatusDTO {
    private String vacationId;
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
