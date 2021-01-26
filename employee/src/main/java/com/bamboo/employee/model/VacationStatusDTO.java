package com.bamboo.employee.model;

public class VacationStatusDTO {

    private String vacationId;
    private String vacationStatus;

    public VacationStatusDTO() {
    }

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    public String getVacationStatus() {
        return vacationStatus;
    }

    public void setVacationStatus(String vacationStatus) {
        this.vacationStatus = vacationStatus;
    }
}
