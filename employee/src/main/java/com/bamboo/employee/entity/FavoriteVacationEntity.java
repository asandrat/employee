package com.bamboo.employee.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter @Setter
@Entity
@Table(name = "favorite_vacation")
public class FavoriteVacationEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "favorite_vacation_generator"
    )
    @SequenceGenerator(
            name = "favorite_vacation_generator",
            sequenceName = "favorite_vacation_sequence",
            allocationSize = 1)
    private int id;

    @Column(name = "month_number")
    private int monthValue;

    @Column(name = "creation_timestamp", updatable = false)
    @CreationTimestamp
    private Timestamp creationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "employee_id"
    )
    private EmployeeEntity employeeEntity;
}
