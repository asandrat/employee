package com.bamboo.employee.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

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


    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "month_number")
    private int monthValue;

    @Column(name = "creation_timestamp", updatable = false)
    @CreationTimestamp
    private Timestamp creationTime;

    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("employeeId")
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private EmployeeEntity employeeEntity;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(final int employeeId) {
        this.employeeId = employeeId;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(final int monthValue) {
        this.monthValue = monthValue;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(final EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }
}
