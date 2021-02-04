package com.bamboo.employee.service;

import com.bamboo.employee.entity.VacationStatus;
import com.bamboo.employee.model.VacationDTO;

import java.util.List;

public interface VacationService {

    VacationDTO addVacation(
            int employeeId,
            String dateFrom,
            String dateTo,
            String status
    );

    void removeVacation(int vacationId, int employeeId);

    VacationDTO findVacation(int employeeId, int vacationId);

    List<VacationDTO> getVacations(int employeeId);

    void changeVacationStatus(
            int vacationId,
            int employeeId,
            VacationStatus status
    );
}
