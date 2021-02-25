package com.bamboo.employee.service.vacation;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.entitiesDB.VacationStatus;
import com.bamboo.employee.mapstruct.VacationMapper;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repositoryDB.employee.EmployeeRepositoryDB;
import com.bamboo.employee.repositoryDB.vacation.VacationRepositoryDB;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class VacationServiceImpl implements VacationService {
    private final VacationRepositoryDB vacationRepositoryDB;
    private final EmployeeRepositoryDB employeeRepositoryDB;
    private final VacationMapper vacationMapper;

    public VacationServiceImpl(VacationRepositoryDB vacationRepositoryDB,
                               EmployeeRepositoryDB employeeRepositoryDB,
                               //ModelMapper modelMapper) {
                               VacationMapper vacationMapper) {
        this.vacationRepositoryDB = vacationRepositoryDB;
        this.employeeRepositoryDB = employeeRepositoryDB;
        this.vacationMapper = vacationMapper;
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

        VacationStatus vacationStatus = VacationStatus.fromString(status);
        Vacation vacation = new Vacation(employee, dateFrom, dateTo,
                vacationStatus);

        vacationRepositoryDB.addVacationToEmployee(vacation);
        VacationDTO vacationDTO =
                vacationMapper.vacationEntityToVacationDTO(vacation);
        vacationDTO.setEmployeeId(employeeId);
        return vacationDTO;
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
        List<Vacation> listOfVacations =
                new ArrayList<>(vacationRepositoryDB.findAllVacations());
        Collection<VacationDTO> vacationDTOList = new ArrayList<>();
        String emplpyeeId;
        for (Vacation vacation : listOfVacations) {
            VacationDTO vacationDTO = vacationMapper
                    .vacationEntityToVacationDTO(vacation);
            emplpyeeId = String.valueOf(vacation.getEmployee().getId());
            vacationDTO.setEmployeeId(emplpyeeId);
            vacationDTOList.add(vacationDTO);
        }
        return vacationDTOList;
    }

    @Override
    public VacationDTO findById(String id) {
        long longId = Long.parseLong(id);
        Vacation vacation = vacationRepositoryDB.findVacationById(longId);
        return vacationMapper.vacationEntityToVacationDTO(vacation);
    }
}
