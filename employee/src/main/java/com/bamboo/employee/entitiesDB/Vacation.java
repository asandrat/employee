package com.bamboo.employee.entitiesDB;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Vacation")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Transient
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VacationStatus status;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;


    public Vacation(Employee employee, LocalDate dateFrom,
                    LocalDate dateTo, VacationStatus status) {
        this.employee = employee;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
        this.duration = (int) Duration.between(dateFrom.atStartOfDay(),
                dateTo.atStartOfDay()).toDays();
    }

    public Vacation(Employee employee, LocalDate dateFrom,
                    LocalDate dateTo, VacationStatus status, int duration) {
        this.employee = employee;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
        this.duration = duration;
    }
    public int getDuration() {
        return (int) Duration.between(dateFrom.atStartOfDay(),
                dateTo.atStartOfDay()).toDays();
    }
}
