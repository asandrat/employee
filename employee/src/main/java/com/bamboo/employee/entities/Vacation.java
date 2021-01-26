package com.bamboo.employee.entities;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@NotNull
public class Vacation implements Serializable {
    private String id; //required
    private String employeeId; //required
    private LocalDate dateFrom; //required
    private LocalDate dateTo; //required
    private int duration; //number of days
    private VacationStatus status; //required

    public Vacation(final String id, final String employeeId, final LocalDate dateFrom,
                    final LocalDate dateTo, final int duration,
                    final VacationStatus status) {
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

    public void setId(final String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(final String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(final LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(final LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public VacationStatus getStatus() {
        return status;
    }

    public void setStatus(final VacationStatus status) {
        this.status = status;
    }
}
