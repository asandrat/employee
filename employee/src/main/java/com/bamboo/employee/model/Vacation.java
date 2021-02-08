package com.bamboo.employee.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Vacation {
    private int id;
    private LocalDate from;
    private LocalDate to;
    private long duration;
    private VacationStatus status;

    public Vacation() {
    }

    public Vacation(final int id, final LocalDate from, final LocalDate to,
                    final VacationStatus status) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.status = status;
        this.duration = ChronoUnit.DAYS.between(from, to);
    }

    public Vacation(final Vacation other) {
        this.id = other.getId();
        this.from = other.getFrom();
        this.to = other.getTo();
        this.duration = other.getDuration();
        this.status = other.getStatus();
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
        return duration;
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
}
