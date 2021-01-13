package com.bamboo.employee.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String uniqueId;
    private String name;
    private String surname;
    private List<Vacation> vacations;

    public Employee() { }

    public Employee(String uniqueId, String name, String surname) {
        this.uniqueId = uniqueId;
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
