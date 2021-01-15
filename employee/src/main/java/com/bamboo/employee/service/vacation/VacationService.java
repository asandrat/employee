package com.bamboo.employee.service.vacation;

import com.bamboo.employee.model.Vacation;

import java.util.Map;

public interface VacationService {

    void addVacation(String employeeId, String dateFrom, String dateTo,
                     String status);

    void removeVacation(String id);

    void approveVacation(String vacationId);

    void rejectVacation(String vacationId);

    Map<String, Vacation> findAll();
}
