package com.bamboo.employee.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NotNull
public class Employee implements Serializable {

    private int id; //required
    private String name; //required
    private String surname; //required
    private List<Vacation> vacations;

    public Employee(final int id, final String name, final String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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

    public List<Vacation> getVacations() {
        if (vacations == null) {
            vacations = new ArrayList<>();
        }
        return vacations;
    }

    public void setVacations(final List<Vacation> vacations) {
        if (this.vacations.size() == 0) {
            this.vacations.addAll(vacations);
        } else {
            this.vacations = vacations;
        }
    }
}
