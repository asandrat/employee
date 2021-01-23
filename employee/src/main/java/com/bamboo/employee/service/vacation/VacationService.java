package com.bamboo.employee.service.vacation;

import com.bamboo.employee.entities.Vacation;
import com.bamboo.employee.model.VacationDTO;

import java.util.List;
import java.util.Map;

public interface VacationService {

    VacationDTO addVacation(String employeeId, String dateFrom, String dateTo,
                            String status);

    void removeVacation(String id);

    void approveVacation(String vacationId);

    void rejectVacation(String vacationId);

    List<VacationDTO> findAll();

    void saveAllVacations(Map<String, Vacation> vacationMap);
}
