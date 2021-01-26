package com.bamboo.employee.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class VacationDTO {

    private String id;

    @NotBlank(message = "Date from is required")
    @Pattern(regexp = "([0-9]{2})\\([0-9]{2}\\([0-9]{4}")
    private String dateFrom;

    @NotBlank(message = "Date to is required")
    @Pattern(regexp = "([0-9]{2})\\([0-9]{2}\\([0-9]{4}")
    private String dateTo;

    @Positive
    private long duration;

    @NotBlank(message = "Vacation status is required")
    private String vacationStatus;

    public VacationDTO() {
    }

    public VacationDTO(
            String id,
            String dateFrom,
            String dateTo,
            long duration,
            String vacationStatus
    ) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.vacationStatus = vacationStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getVacationStatus() {
        return vacationStatus;
    }

    public void setVacationStatus(String vacationStatus) {
        this.vacationStatus = vacationStatus;
    }
}
