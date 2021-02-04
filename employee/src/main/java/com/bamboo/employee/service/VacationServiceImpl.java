package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.ApplicationException;
import com.bamboo.employee.custom.exception.VacationNotFoundException;
import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.entity.VacationStatus;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repository.EntityRepository;
import com.bamboo.employee.repository.VacationRepository;
import com.bamboo.employee.validator.VacationStateTransitionValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VacationServiceImpl implements VacationService {

    private final EmployeeService employeeService;
    private final VacationRepository vacationRepository;
    private final VacationStateTransitionValidator vacationStateTransitionValidator;
    private final ModelMapper modelMapper;

    EntityRepository<Vacation> dao;

    @Autowired
    public void setDao(EntityRepository<Vacation> daoToSet) {
        dao = daoToSet;
        dao.setEntityType(Vacation.class);
    }

    @Override
    @Transactional
    public VacationDTO addVacation(
            int employeeId,
            String dateFrom,
            String dateTo,
            String status
    ) {
        Employee employee = employeeService.checkIfEmployeeExists(employeeId);
        Vacation vacation = createVacation(dateFrom, dateTo, status);
        employee.addVacation(vacation);
        Vacation dbVacation = vacationRepository.save(vacation);
        return modelMapper.map(dbVacation, VacationDTO.class);
    }

    @Override
    @Transactional
    public void removeVacation(int vacationId, int employeeId) {
        checkIfVacationBelongsToTheEmployee(
                vacationId,
                employeeId
        );
        vacationRepository.deleteById(vacationId);
    }

    @Transactional
    @Override
    public void changeVacationStatus(
            int vacationId,
            int employeeId,
            VacationStatus status
    ) {
        Vacation vacation = checkIfVacationBelongsToTheEmployee(
                vacationId,
                employeeId
        );
        vacationStateTransitionValidator.validateVacationTransitionStatus(
                vacation,
                status);
        vacationRepository.changeVacationStatus(vacation, status);
    }

    @Override
    @Transactional
    public VacationDTO findVacation(int employeeId, int vacationId) {
        Vacation vacation = checkIfVacationBelongsToTheEmployee(
                vacationId,
                employeeId
        );
        return modelMapper.map(vacation, VacationDTO.class);
    }

    @Override
    @Transactional
    public List<VacationDTO> getVacations(int employeeId) {
        Employee employee = employeeService.checkIfEmployeeExists(employeeId);
        List<Vacation> vacationList = vacationRepository.findAll(employee);
        if (vacationList.isEmpty()) {
            throw new VacationNotFoundException(
                    "Could not find any vacation for the employee with id: "
                            + employeeId
            );
        }
        Type listType = new TypeToken<List<VacationDTO>>(){}.getType();
        return modelMapper.map(vacationList,listType);
    }

    public Vacation createVacation(
            String dateFrom,
            String dateTo,
            String status
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDateFrom =  LocalDate.parse(dateFrom, formatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, formatter);
        if (localDateFrom.isAfter(localDateTo)) {
            throw new ApplicationException(
                    "Date to must be after Date from", HttpStatus.BAD_REQUEST
            );
        }
        long duration = Duration.between(localDateFrom.atStartOfDay(),
                localDateTo.atStartOfDay()).toDays();

        return new Vacation(
                localDateFrom,
                localDateTo,
                duration,
                VacationStatus.valueOf(status.toUpperCase())
        );
    }

    private Vacation checkIfVacationBelongsToTheEmployee(
            int vacationId,
            int employeeId
    ) {
        Employee employee = employeeService.checkIfEmployeeExists(employeeId);
        return checkIfVacationBelongsToTheEmployee(employee, vacationId);
    }

    public Vacation checkIfVacationBelongsToTheEmployee(
            Employee employee,
            int vacationId
    ) {
        Vacation vacation =  vacationRepository.findById(vacationId);

        if (vacation == null || !employee.getVacations().contains(vacation)) {
            throw new VacationNotFoundException(
                    "Could not find vacation with id: " + vacationId
                            + " for the employee with id: "
                            + employee.getUniqueId()
            );
        }
        return vacation;
    }
}
