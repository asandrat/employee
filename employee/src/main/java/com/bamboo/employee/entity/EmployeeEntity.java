package com.bamboo.employee.entity;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_generator"
    )
    @SequenceGenerator(
            name = "employee_generator",
            sequenceName = "employee_sequence",
            allocationSize = 1)
    @Column(name = "id")
    private int uniqueId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;

    @Column(name = "creation_timestamp")
    @CreationTimestamp
    private Timestamp creationTime;


    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Collection<VacationEntity> vacations = new ArrayList<>();

    public Collection<VacationEntity> getVacations() {
        return vacations;
    }

    public void addVacation(VacationEntity vacationEntity) {
        vacations.add(vacationEntity);
        vacationEntity.setEmployee(this);
    }

    public void removeVacation(VacationEntity vacationEntity) {
        vacations.remove(vacationEntity);
        vacationEntity.setEmployee(null);
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(final int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}
