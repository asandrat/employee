package com.bamboo.employee.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@NotNull
public class Vacation implements Serializable {
    private int id; //required
    private int employeeId; //required
    private LocalDate dateFrom; //required
    private LocalDate dateTo; //required
    private int duration; //number of days
    private VacationStatus status; //required

    public Vacation(final int id, final int employeeId, final LocalDate dateFrom,
                    final LocalDate dateTo, final int duration,
                    final VacationStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(final int employeeId) {
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
