package com.bamboo.employee.entitiesDB;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "Vacation")
public class Vacation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Transient
    private int duration;

    @Column(name = "status")
    private String status;

    public Vacation() {
    }

    public Vacation(long id, long employeeId, LocalDate dateFrom,
                      LocalDate dateTo, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
    }

    public Vacation(long employeeId, LocalDate dateFrom,
                      LocalDate dateTo, String status) {
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
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
        return (int) Duration.between(dateFrom.atStartOfDay(),
                dateTo.atStartOfDay()).toDays();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
