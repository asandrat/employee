package com.bamboo.employee.model;

import java.util.*;

public class Employee {

    private int uniqueId;
    private String name;
    private String surname;
    private final Map<VacationId, Vacation> vacations = new HashMap<>();

    public Employee(final int uniqueId,
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
        return new HashMap<>(vacations).values();
    }

    public void setUniqueId(final int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Vacation addVacation(final Vacation vacation) {
        return vacations.put(vacation.getId(), new Vacation(vacation));
    }

    public Optional<Vacation> getVacation(final VacationId id) {
        return Optional.ofNullable(vacations.get(id));
    }

    public Vacation removeVacation(final VacationId id) {
        return vacations.remove(id);
    }

    public void updateVacation(final VacationId id,
                               final VacationStatus status) {
        Vacation v = getVacation(id).get();
        v.setStatus(status);
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
        return uniqueId == employee.uniqueId;
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
