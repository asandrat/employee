package com.bamboo.employee.model;

import com.bamboo.employee.entities.VacationStatus;

import java.time.LocalDate;

public class VacationDTO {
    private String id;
    private String employeeId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int duration;
    private VacationStatus status;

    public VacationDTO() {
    }

    public VacationDTO(String id, String employeeId, LocalDate dateFrom,
                       LocalDate dateTo, int duration, VacationStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public VacationStatus getStatus() {
        return status;
    }

    public void setStatus(VacationStatus status) {
        this.status = status;
    }
}
