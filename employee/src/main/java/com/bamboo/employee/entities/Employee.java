package com.bamboo.employee.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Employee {
    private String uniqueId;
    private String name;
    private String surname;
    private List<Vacation> vacations;

    public Employee() { }

    public Employee(String name, String surname) {
        this.uniqueId = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setVacations(List<Vacation> vacations) {
        this.vacations = vacations;
    }

    public void addVacation(Vacation vacation) {
        if (vacations == null) {
            vacations = new ArrayList<>();
        }
        vacations.add(vacation);
    }

    public List<Vacation> getVacations() {
        return vacations;
    }

    public void removeVacation(Vacation vacation) {
        vacations.remove(vacation);
    }

}
