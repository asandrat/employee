package com.bamboo.employee.entity;

import com.bamboo.employee.model.VacationStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "vacation")
public class VacationEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "from_date")
    private LocalDate from;

    @Column(name = "to_date")
    private LocalDate to;

    @Transient
    private long duration;

    @Enumerated(EnumType.STRING)
    private VacationStatus status;

    @ManyToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id"
    )
    private EmployeeEntity employee;

    public VacationEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(final LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(final LocalDate to) {
        this.to = to;
    }

    public long getDuration() {
        return ChronoUnit.DAYS.between(from, to);
    }

    public void setDuration(final long duration) {
        this.duration = duration;
    }

    public VacationStatus getStatus() {
        return status;
    }

    public void setStatus(final VacationStatus status) {
        this.status = status;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(final EmployeeEntity employee) {
        this.employee = employee;
    }
}
