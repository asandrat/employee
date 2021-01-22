package com.bamboo.employee.service.vacation;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.vacation.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Service
public class VacationServiceImpl implements VacationService {
    @Autowired
    private VacationRepository vacationRepository;

    @Override
    public void addVacation(String employeeId, String dateFromString,
                            String dateToString, String status) {
        final String id = UUID.randomUUID().toString();
        System.out.println("service vacation: " + id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd");
        LocalDate dateFrom = LocalDate.parse(dateFromString,
                formatter);
        LocalDate dateTo = LocalDate.parse(dateToString, formatter);
        int duration = (int) Duration.between(dateFrom.atStartOfDay(),
                dateTo.atStartOfDay()).toDays();
        VacationStatus vacationStatus = VacationStatus.fromString
                (status);
        Vacation vacation = new Vacation(id, employeeId, dateFrom, dateTo,
                duration, vacationStatus);

        vacationRepository.addVacationToEmployee(vacation);
    }

    @Override
    public void removeVacation(String id) {
        vacationRepository.removeVacation(id);
    }

    @Override
    public void approveVacation(String id) {
        vacationRepository.approveVacation(id);
    }

    @Override
    public void rejectVacation(String id) {
        vacationRepository.rejectVacation(id);
    }

    @Override
    public Map<String, Vacation> findAll() {
        return vacationRepository.findAll();
    }

    @Override
    public void saveAllVacations(Map<String, Vacation> vacationMap) {
        vacationRepository.saveAllVacations(vacationMap);
    }
}
