package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final VacationValidator vacationValidator;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            VacationValidator vacationValidator
    ) {
        this.employeeRepository = employeeRepository;
        this.vacationValidator = vacationValidator;
    }

    @Override
    public void addEmployee(String name, String surname) {
        Employee employee = createEmployee(name, surname);
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void removeEmployee(String employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }

    @Override
    public void addVacation(
            String employeeId,
            String dateFrom,
            String dateTo,
            String status
    ) {
        Vacation vacation = createVacation(dateFrom, dateTo, status);
        employeeRepository.addVacationToEmployee(employeeId, vacation);
    }

    @Override
    public void removeVacation(String vacationId, String employeeUniqueId) {
        employeeRepository.removeVacation(vacationId, employeeUniqueId);
    }

    @Override
    public void approveVacation(String vacationId, String employeeUniqueId) {
        Employee employee = employeeRepository.findEmployee(employeeUniqueId);
        Vacation vacation = employeeRepository.findVacation(employee, vacationId);
        vacationValidator.validateVacationTransitionStatus(
                vacation,
                VacationStatus.APPROVED);
        employeeRepository.approveVacation(vacation);
    }

    @Override
    public void rejectVacation(String vacationId, String employeeUniqueId) {
        Employee employee = employeeRepository.findEmployee(employeeUniqueId);
        Vacation vacation = employeeRepository.findVacation(employee, vacationId);
        vacationValidator.validateVacationTransitionStatus(
                vacation,
                VacationStatus.REJECTED);
        employeeRepository.rejectVacation(vacation);
    }

    private Employee createEmployee(String name, String surname) {
        String employeeId = UUID.randomUUID().toString();
        return new Employee(employeeId, name, surname);
    }

    private Vacation createVacation(
            String dateFrom,
            String dateTo,
            String status
    ) {
        final String vacationId = UUID.randomUUID().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDateFrom =  LocalDate.parse(dateFrom, formatter);
        LocalDate localDateTo = LocalDate.parse(dateTo, formatter);
        long duration = Duration.between(localDateFrom.atStartOfDay(),
                localDateTo.atStartOfDay()).toDays();

        return new Vacation(
                vacationId,
                localDateFrom,
                localDateTo,
                duration,
                VacationStatus.valueOf(status.toUpperCase())
        );
    }
}
