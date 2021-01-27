package com.bamboo.employee.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Employee {
    private String uniqueId;
    private String name;
    private String surname;
    private List<Vacation> vacations;


    public Employee(String name, String surname) {
        this.uniqueId = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
    }

    public void addVacation(Vacation vacation) {
        if (vacations == null) {
            vacations = new ArrayList<>();
        }
        vacations.add(vacation);
    }

    public void removeVacation(Vacation vacation) {
        vacations.remove(vacation);
    }

}
