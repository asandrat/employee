package com.bamboo.employee.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int uniqueId;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "employee")
    private List<Vacation> vacations;

    public void addVacation(Vacation vacation) {
        if (vacations == null) {
            vacations = new ArrayList<>();
        }
        vacations.add(vacation);
        vacation.setEmployee(this);
    }
}
