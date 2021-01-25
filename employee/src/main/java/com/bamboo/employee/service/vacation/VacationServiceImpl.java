package com.bamboo.employee.service.vacation;

import com.bamboo.employee.entities.Vacation;
import com.bamboo.employee.entities.VacationStatus;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repository.vacation.VacationRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VacationServiceImpl implements VacationService {
    @Autowired
    private VacationRepository vacationRepository;

    private final ModelMapper modelMapper;

    public VacationServiceImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public VacationDTO addVacation(String employeeId, String dateFromString,
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
        return modelMapper.map(vacation, VacationDTO.class);
    }

    @Override
    public boolean removeVacation(String id) {
        return vacationRepository.removeVacation(id);
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
    public List<VacationDTO> findAll() {
        List<Vacation> list =
                new ArrayList<>(vacationRepository.findAll().values());
        Type listType = new TypeToken<List<VacationDTO>>(){}.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public void saveAllVacations(Map<String, Vacation> vacationMap) {
        vacationRepository.saveAllVacations(vacationMap);
    }
}
