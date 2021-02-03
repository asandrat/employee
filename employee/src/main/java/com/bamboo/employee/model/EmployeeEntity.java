package com.bamboo.employee.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int uniqueId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;


    @OneToMany(mappedBy = "employee")
    private Collection<VacationEntity> vacations;

    public void addVacation(VacationEntity vacation) {
        vacations.add(vacation);
        vacation.setEmployee(this);
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
}
