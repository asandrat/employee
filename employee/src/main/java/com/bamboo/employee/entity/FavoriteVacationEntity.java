package com.bamboo.employee.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "favorite_vacation")
public class FavoriteVacationEntity {

    @Id
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "month_number")
    private int monthValue;

    @Column(name = "creation_timestamp", updatable = false)
    @CreationTimestamp
    private Timestamp creationTime;


    // todo prebaciti u manyToOne
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeEntity;

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
