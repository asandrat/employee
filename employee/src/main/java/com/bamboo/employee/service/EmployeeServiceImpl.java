package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.custom.exception.VacationNotFoundException;
import com.bamboo.employee.entities.Employee;
import com.bamboo.employee.entities.Vacation;
import com.bamboo.employee.entities.VacationStatus;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final VacationValidator vacationValidator;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            VacationValidator vacationValidator
    ) {
        this.employeeRepository = employeeRepository;
        this.vacationValidator = vacationValidator;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public EmployeeDTO addEmployee(String name, String surname) {
        Employee employee = new Employee(name, surname);
        employeeRepository.addEmployee(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public void removeEmployee(String employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }

    @Override
    public VacationDTO addVacation(
            String employeeId,
            String dateFrom,
            String dateTo,
            String status
    ) {
        Vacation vacation = createVacation(dateFrom, dateTo, status);
        employeeRepository.addVacationToEmployee(employeeId, vacation);
        return modelMapper.map(vacation, VacationDTO.class);
    }

    @Override
    public void removeVacation(String vacationId, String employeeUniqueId) {
        employeeRepository.removeVacation(vacationId, employeeUniqueId);
    }

    @Override
    public void approveVacation(String vacationId, String employeeUniqueId) {
        Vacation vacation = employeeRepository.findVacation(employeeUniqueId, vacationId);
        vacationValidator.validateVacationTransitionStatus(
                vacation,
                VacationStatus.APPROVED);
        employeeRepository.approveVacation(vacation);
    }

    @Override
    public void rejectVacation(String vacationId, String employeeUniqueId) {
        Vacation vacation = employeeRepository.findVacation(employeeUniqueId, vacationId);
        vacationValidator.validateVacationTransitionStatus(
                vacation,
                VacationStatus.REJECTED);
        employeeRepository.rejectVacation(vacation);
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.findAll().values());
        Type listType = new TypeToken<List<EmployeeDTO>>(){}.getType();
        return modelMapper.map(employeeList,listType);
    }

    @Override
    public EmployeeDTO getEmployee(String id) {
        Employee employee = employeeRepository.findEmployee(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id: " + id
            );
        }
        return modelMapper.map(
                employee,
                EmployeeDTO.class
        );
    }

    @Override
    public VacationDTO findVacation(String employeeId, String vacationId) {
        return modelMapper.map(employeeRepository.findVacation(
                employeeId,
                vacationId
        ), VacationDTO.class);
    }

    @Override
    public List<VacationDTO> getVacations(String employeeId) {
        Employee employee = employeeRepository.findEmployee(employeeId);
        List<Vacation> vacationList = employee.getVacations();
        if (vacationList == null) {
            throw new VacationNotFoundException(
                    "There is no vacations for employee with id " + employeeId
            );
        }
        Type listType = new TypeToken<List<VacationDTO>>(){}.getType();
        return modelMapper.map(vacationList,listType);
    }

    private Vacation createVacation(
            String dateFrom,
            String dateTo,
            String status
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDateFrom =  LocalDate.parse(dateFrom, formatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, formatter);
        long duration = Duration.between(localDateFrom.atStartOfDay(),
                localDateTo.atStartOfDay()).toDays();

        return new Vacation(
                localDateFrom,
                localDateTo,
                duration,
                VacationStatus.valueOf(status.toUpperCase())
        );
    }
}
