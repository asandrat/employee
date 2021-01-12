package com.bamboo.employee.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {

    private Integer uniqueId;
    private String name;
    private String surname;
    private List<Vacation> vacations = new ArrayList<>();


    public Employee(final Integer uniqueId,
                    final String name,
                    final String surname) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.surname = surname;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Vacation> getVacations() {
        return vacations;
    }


    public void setUniqueId(final Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public void addVacation(final Vacation vacation) {
        this.vacations.add(vacation);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return uniqueId.equals(employee.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
        String vacations = this.vacations != null
                ? this.vacations.toString()
                : "";

        return "Employee{"
                + "uniqueId=" + uniqueId
                + ", name='" + name
                + '\'' + ", surname='" + surname
                + '\'' + ", vacations=" + vacations
                + '}';
    }
}
