package com.bamboo.employee.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

public class Vacation {
    private VacationId id;
    private LocalDate from;
    private LocalDate to;
    private long duration;
    private VacationStatus status;

    public Vacation(final Vacation other) {
        this.id = other.id;
        this.from = other.from;
        this.to = other.to;
        this.duration = other.duration;
        this.status = other.status;
    }


    public Vacation(final Integer employeeId,
                    final Integer vacationId,
                    final LocalDate from,
                    final LocalDate to,
                    final String duration,
                    final VacationStatus status) {
        this.id = new VacationId(employeeId, vacationId);
        this.from = from;
        this.to = to;
        this.duration = duration == null
                ? Math.abs(ChronoUnit.DAYS.between(from, to))
                : Long.parseLong(duration);
        this.status = status;
    }

    public Vacation(VacationId id) {
        this.id = id;
    }

    public VacationId getId() {
        return id;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public long getDuration() {
        return duration;
    }

    public VacationStatus getStatus() {
        return status;
    }


    public void setStatus(VacationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacation vacation = (Vacation) o;
        return id.equals(vacation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vacation{"
                + "id=" + id
                + ", from=" + from
                + ", to=" + to
                + ", duration=" + duration
                + ", status=" + status
                + '}';
    }
}
