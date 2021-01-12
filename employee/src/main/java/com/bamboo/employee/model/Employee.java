package com.bamboo.employee.model;


import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Employee {

    private Integer uniqueId;
    private String name;
    private String surname;
    private Set<Vacation> vacations = new HashSet<>();


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

    public Collection<Vacation> getVacations() {
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

    public void removeVacation(final VacationId id) {
        Vacation vacation = new Vacation(id);
        vacations.remove(vacation);
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
        return "Employee{"
                + "uniqueId=" + uniqueId
                + ", name='" + name
                + '\'' + ", surname='" + surname
                + '\'' + ", vacations=" + vacations.toString()
                + '}';
    }
}
