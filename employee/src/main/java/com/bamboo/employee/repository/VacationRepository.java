package com.bamboo.employee.repository;

import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.entity.VacationStatus;

import java.util.List;

public interface VacationRepository {

    Vacation save(Employee employee, Vacation vacation);

    Vacation findById(int vacationId);

    List<Vacation> findAll(Employee employee);

    void deleteById(int vacationId);

    void changeVacationStatus(Vacation vacation, VacationStatus status);

}

