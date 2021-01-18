package com.bamboo.employee.repository;

import com.bamboo.employee.custom.exception.EmployeeFileNotFoundException;
import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.custom.exception.EmployeeStorageException;
import com.bamboo.employee.custom.exception.VacationNotFoundException;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final String fileName;

    private Map<String, Employee> employeeList;
    private final ObjectMapper objectMapper;



    public EmployeeRepositoryImpl(
            @Value("${spring.employeeApp.file.path}") String fileName,
            ObjectMapper objectMapper
    ) {
        this.fileName = fileName;
        this.objectMapper = objectMapper;
        employeeList = findAll();
        if (employeeList == null) {
            employeeList = new HashMap<>();
        }
    }

    @Override
    public Map<String, Employee> findAll() {
        try {
            employeeList = objectMapper.readValue(
                    new File(fileName),
                    new TypeReference<Map<String, Employee>>() {
                    }
            );
        } catch (IOException e) {
            throw new EmployeeFileNotFoundException(
                    "Could not read employees from file " + fileName, e
            );
        }
        return employeeList;
    }

    @Override
    public void saveAll(Map<String, Employee> employees) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(
                    new File(fileName),
                    employeeList
            );
        } catch (IOException e) {
            throw new EmployeeStorageException(
                    "Could not save changes to file " + fileName, e
            );
        }
    }

    @Override
    public void addEmployee(Employee emp) {
        employeeList.put(emp.getUniqueId(), emp);
        saveAll(employeeList);
    }

    @Override
    public Employee findEmployee(String uniqueId) {
        Employee employee = employeeList.get(uniqueId);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id: " + uniqueId
            );
        }
        return employee;
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeeList.remove(employeeId);
        saveAll(employeeList);
    }

    @Override
    public void addVacationToEmployee(String employeeId, Vacation vacation) {
        Employee employee = findEmployee(employeeId);
        employee.addVacation(vacation);
        saveAll(employeeList);
    }

    @Override
    public void removeVacation(String vacationId, String employeeUniqueId) {
        Employee employee = findEmployee(employeeUniqueId);
        Vacation vacation = findVacation(employee, vacationId);
        employee.removeVacation(vacation);
        saveAll(employeeList);
    }

    @Override
    public void approveVacation(Vacation vacation) {
        vacation.approveRequest();
        saveAll(employeeList);
    }

    @Override
    public void rejectVacation(Vacation vacation) {
        vacation.rejectRequest();
        saveAll(employeeList);
    }

    public Vacation findVacation(Employee employee, String vacationId) {
        return employee.getVacations().stream()
                .filter(
                        vacation -> vacation.getUniqueId().equals(vacationId)
                ).findFirst()
                .orElseThrow(() -> new VacationNotFoundException(
                        "Could not find vacation with id " + vacationId
                                + "for the given employee"
                ));
    }
}
