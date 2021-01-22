package com.bamboo.employee.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.UUID;

public class Vacation {

    private String uniqueId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateTo;
    private long duration;

    @JsonIgnore
    private Employee employee;

    VacationStatus vacationStatus;

    public Vacation() { }

    public Vacation(
            LocalDate dateFrom,
            LocalDate dateTo,
            long duration,
            VacationStatus vacationStatus
    ) {
        this.uniqueId = UUID.randomUUID().toString();
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.vacationStatus = vacationStatus;
    }

    public void approveRequest() {
        vacationStatus = VacationStatus.APPROVED;
    }

    public void rejectRequest() {
        vacationStatus = VacationStatus.REJECTED;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public VacationStatus getVacationStatus() {
        return vacationStatus;
    }

    public void setVacationStatus(VacationStatus vacationStatus) {
        this.vacationStatus = vacationStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
