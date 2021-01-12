package com.bamboo.employee.service;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Map<String, Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveAll(Map<String, Employee> map) {
        //write to file
        employeeRepository.saveAll(map);
    }

    @Override
    public void addEmployee(String name, String surname) {
        final String id = UUID.randomUUID().toString();
        System.out.println("service: "+id);
        Employee employee = new Employee(id, name, surname);
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void removeEmployee(String id) {
        employeeRepository.removeEmployee(id);
    }

    @Override
    public void addVacation(String id, String employeeId, String dateFromString,
                            String dateToString, String status) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd");

        LocalDate dateFrom = LocalDate.parse(dateFromString, formatter);
        LocalDate dateTo = LocalDate.parse(dateToString, formatter);

        int duration = (int) Duration.between(dateFrom.atStartOfDay(),
                dateTo.atStartOfDay()).toDays();

        VacationStatus vacationStatus = VacationStatus.fromString(status);

        Vacation vacation = new Vacation(id, employeeId, dateFrom, dateTo
                , duration, vacationStatus);

        employeeRepository.addVacationToEmployee(employeeId, vacation);
    }

    @Override
    public void removeVacation(String id, String employeeId) {
        employeeRepository.removeVacation(id,employeeId);
    }

    @Override
    public void approveVacation(String id, String employeeId, String status) {

    }

    @Override
    public void rejectVacation(String id, String employeeId, String status) {

    }


}
