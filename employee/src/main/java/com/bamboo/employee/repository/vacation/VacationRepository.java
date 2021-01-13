package com.bamboo.employee.repository.vacation;

import com.bamboo.employee.model.Vacation;

import java.util.Map;

public interface VacationRepository {
    void addVacationToEmployee(String employeeId, Vacation vacation);

    Map<String, Vacation> findAll();

    void saveAllVacations(Map<String, Vacation> map);

    void removeVacation(String id);

    void approveVacation(String id);

    void rejectVacation(String id);
}
