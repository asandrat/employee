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

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Map<Integer, Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveAll(Map<Integer, Employee> map) {
        //write to file
        employeeRepository.saveAll(map);
    }

    @Override
    public void addEmployee(int id, String name, String surname) {
        Employee employee = new Employee(id, name, surname);
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void removeEmployee(int id) {
        employeeRepository.removeEmployee(id);
    }

    @Override
    public void addVacation(int id, int employeeId, String dateFromString,
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
    public void removeVacation(int id, int employeeId) {
        employeeRepository.removeVacation(id,employeeId);
    }

    @Override
    public void approveVacation(int id, int employeeId, String status) {

    }

    @Override
    public void rejectVacation(int id, int employeeId, String status) {

    }


}
