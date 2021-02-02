package com.bamboo.employee.service.vacation;

import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.entitiesFile.VacationFile;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repositoryDB.vacation.VacationRepositoryDB;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VacationServiceImpl implements VacationService {
    private final VacationRepositoryDB vacationRepositoryDB;
    private final ModelMapper modelMapper;

    public VacationServiceImpl(VacationRepositoryDB vacationRepositoryDB) {
        this.vacationRepositoryDB = vacationRepositoryDB;
        this.modelMapper = new ModelMapper();
    }

    @Override
    @Transactional
    public VacationDTO addVacation(String employeeId, String dateFromString,
                                   String dateToString, String status) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateFrom = LocalDate.parse(dateFromString, formatter);
        LocalDate dateTo = LocalDate.parse(dateToString, formatter);

        long longEmployeeId = Long.parseLong(employeeId);

        Vacation vacation = new Vacation(longEmployeeId, dateFrom, dateTo,
                status);

        vacationRepositoryDB.addVacationToEmployee(vacation);
        return modelMapper.map(vacation, VacationDTO.class);
    }

    @Override
    @Transactional
    public void removeVacation(String id) {
        long longId = Long.parseLong(id);
        System.out.println("BRISANJE VACATION " + id + " SERVIS");
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        if (vacation == null) {
            System.out.println("VACATION ID NIJE NADJEN SERVIS");
            throw new IllegalArgumentException(
                    "Vacation with id " + id + " not found.");
        }
        vacationRepositoryDB.deleteVacationById(longId);
    }

    @Override
    @Transactional
    public void approveVacation(String id) {
        long longId = Long.parseLong(id);
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        vacationRepositoryDB.approveVacation(vacation);
    }

    @Override
    @Transactional
    public void rejectVacation(String id) {
        long longId = Long.parseLong(id);
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        vacationRepositoryDB.rejectVacation(vacation);
    }

    @Override
    @Transactional
    public Collection<VacationDTO> findAll() {
        List<Vacation> list =
                new ArrayList<>(vacationRepositoryDB.findAllVacations());
        Type listType = new TypeToken<List<VacationDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public void saveAllVacations(Map<String, VacationFile> vacationMap) {
        //vacationRepository.saveAllVacations(vacationMap);
    }
}
