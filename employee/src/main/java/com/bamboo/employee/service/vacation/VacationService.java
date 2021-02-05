package com.bamboo.employee.service.vacation;

import com.bamboo.employee.entitiesFile.VacationFile;
import com.bamboo.employee.model.VacationDTO;

import java.util.Collection;
import java.util.Map;

public interface VacationService {

    VacationDTO addVacation(String employeeId, String dateFrom, String dateTo,
                            String status);

    void removeVacation(String id);

    void approveVacation(String vacationId);

    void rejectVacation(String vacationId);

    Collection<VacationDTO> findAll();

    void saveAllVacations(Map<String, VacationFile> vacationMap);
}
