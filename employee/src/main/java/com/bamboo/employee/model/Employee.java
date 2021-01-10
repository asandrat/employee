package com.bamboo.employee.model;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

@NotNull
public class Employee {
    //to do: Model Employee's associations

    private int id; //required
    private String name; //required
    private String surname; //required
    private Set<Vacation> vacations;

    public Employee(final int id, final String name, final String surname,
                    final Set<Vacation> vacations) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.vacations = vacations;
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

    public Set<Vacation> getVacations() {
        return vacations;
    }

    public void setVacations(final Set<Vacation> vacations) {
        this.vacations = vacations;
    }
}
