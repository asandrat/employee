package com.bamboo.employee.model;

import com.bamboo.employee.deserializer.VacationDeserializer;
import com.bamboo.employee.model.vacation.state.Approved;
import com.bamboo.employee.model.vacation.state.Rejected;
import com.bamboo.employee.model.vacation.state.Submitted;
import com.bamboo.employee.model.vacation.state.VacationState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(using = VacationDeserializer.class)
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

    VacationStatus vacationStatus;

    private VacationState submitted;
    private VacationState approved;
    private VacationState rejected;
    private VacationState vacationState;

    public Vacation() { }

    public Vacation(
            String uniqueId,
            LocalDate dateFrom,
            LocalDate dateTo,
            long duration,
            VacationStatus vacationStatus
    ) {
        Map<VacationStatus, VacationState> map = new HashMap<>();
        map.put(VacationStatus.APPROVED, new Approved(this));
        map.put(VacationStatus.REJECTED, new Rejected(this));
        map.put(VacationStatus.SUBMITTED, new Submitted(this));

        this.uniqueId = uniqueId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.vacationStatus = vacationStatus;
        this.approved = new Approved(this);
        this.rejected = new Rejected(this);
        this.vacationState = map.get(vacationStatus);
    }

    public void approveRequest() {
        vacationState.approveRequest();
    }

    public void rejectRequest() {
        vacationState.rejectRequest();
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

    public void setVacationState(VacationState vacationState) {
        this.vacationState = vacationState;
    }

    @JsonIgnore
    public VacationState getSubmittedState() {
        return submitted;
    }

    @JsonIgnore
    public VacationState getApprovedState() {
        return approved;
    }

    @JsonIgnore
    public VacationState getRejectedState() {
        return rejected;
    }

    @JsonIgnore
    public VacationState getVacationState() {
        return vacationState;
    }
}
