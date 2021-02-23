package com.bamboo.employee.entity;

import com.bamboo.employee.model.VacationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter @Setter
@Entity
@Table(name = "vacation")
public class VacationEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vacation_generator"
    )
    @SequenceGenerator(name = "vacation_generator", sequenceName =
            "vacation_sequence", allocationSize = 1)
    private int id;

    @Column(name = "from_date")
    private LocalDate from;

    @Column(name = "to_date")
    private LocalDate to;

    @Transient
    @Getter(AccessLevel.NONE) private long duration;

    @Enumerated(EnumType.STRING)
    private VacationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id"
    )
    private EmployeeEntity employee;

    public long getDuration() {
        return ChronoUnit.DAYS.between(from, to);
    }
}
