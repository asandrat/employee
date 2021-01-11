package com.bamboo.employee.model;

import java.time.LocalDate;
import java.util.Objects;

public class Vacation {
    private VacationId id;
    private LocalDate from;
    private LocalDate to;
    private Integer duration;
    private VacationStatus status;

    public Vacation(final Integer employeeId,
                    final Integer vacationId,
                    final LocalDate from,
                    final LocalDate to,
                    final Integer duration,
                    final VacationStatus status) {
        this.id = new VacationId(employeeId, vacationId);
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.status = status;
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

    public Integer getDuration() {
        return duration;
    }

    public VacationStatus getStatus() {
        return status;
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
