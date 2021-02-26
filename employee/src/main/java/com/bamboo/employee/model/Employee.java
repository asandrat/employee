package com.bamboo.employee.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class Employee {

    private int uniqueId;
    private String name;
    private String surname;
    private Timestamp creationTime;

    private final Map<Integer, Vacation> vacations = new HashMap<>();

    public Collection<Vacation> getVacations() {
        return new ArrayList<>(vacations.values());
    }

    public Vacation addVacation(final Vacation vacation) {
        vacations.put(vacation.getId(), new Vacation(vacation));
        return vacations.get(vacation.getId());
    }

    public Optional<Vacation> getVacation(final int id) {
        return Optional.ofNullable(vacations.get(id));
    }

    public Vacation removeVacation(final int id) {
        return vacations.remove(id);
    }

    public void updateVacation(final int id,
                               final VacationStatus status) {
        Vacation v = vacations.get(id);
        v.setStatus(status);
    }
}
