package com.bamboo.employee.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@NotNull
public class Vacation {
    private int id; //required
    private int employeeId; //required
    private Date dateFrom; //required
    private Date dateTo; //required
    private int duration; //number of days
    private VacationStatus status; //required

    public Vacation(final int id, final int employeeId, final Date dateFrom,
                    final Date dateTo, final int duration,
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(final Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(final Date dateTo) {
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
