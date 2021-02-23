package com.bamboo.employee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
public class Vacation {
    private int id;
    private LocalDate from;
    private LocalDate to;
    private long duration;
    private VacationStatus status;

    public Vacation(final int id,
                    final LocalDate from,
                    final LocalDate to,
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
}
