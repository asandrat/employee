package com.bamboo.employee.service.vacation;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.entitiesFile.VacationFile;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repositoryDB.employee.EmployeeRepositoryDB;
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
@Transactional
public class VacationServiceImpl implements VacationService {
    private final VacationRepositoryDB vacationRepositoryDB;
    private final EmployeeRepositoryDB employeeRepositoryDB;
    private final ModelMapper modelMapper;

    public VacationServiceImpl(VacationRepositoryDB vacationRepositoryDB,
                               EmployeeRepositoryDB employeeRepositoryDB) {
        this.vacationRepositoryDB = vacationRepositoryDB;
        this.employeeRepositoryDB = employeeRepositoryDB;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public VacationDTO addVacation(String employeeId, String dateFromString,
                                   String dateToString, String status) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateFrom = LocalDate.parse(dateFromString, formatter);
        LocalDate dateTo = LocalDate.parse(dateToString, formatter);

        long longEmployeeId = Long.parseLong(employeeId);
        Employee employee =
                employeeRepositoryDB.findEmployeeById(longEmployeeId);
        Vacation vacation = new Vacation(employee, dateFrom, dateTo,
                status);

        vacationRepositoryDB.addVacationToEmployee(vacation);
        return modelMapper.map(vacation, VacationDTO.class);
    }

    @Override
    public void removeVacation(String id) {
        long longId = Long.parseLong(id);
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        if (vacation == null) {
            throw new IllegalArgumentException(
                    "Vacation with id " + id + " not found.");
        }
        vacationRepositoryDB.deleteVacation(vacation);
    }

    @Override
    public void approveVacation(String id) {
        long longId = Long.parseLong(id);
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        vacationRepositoryDB.approveVacation(vacation);
    }

    @Override
    public void rejectVacation(String id) {
        long longId = Long.parseLong(id);
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        vacationRepositoryDB.rejectVacation(vacation);
    }

    @Override
    public Collection<VacationDTO> findAll() {
        List<Vacation> list =
                new ArrayList<>(vacationRepositoryDB.findAllVacations());
        Type listType = new TypeToken<List<VacationDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public VacationDTO findById(String id) {
        long longId = Long.parseLong(id);
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        return modelMapper.map(vacation, VacationDTO.class);
    }
}
