package com.bamboo.employee.repository.vacation;

import com.bamboo.employee.entities.Vacation;
import com.bamboo.employee.entities.VacationStatus;
import com.bamboo.employee.repository.FileReaderAndWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Map;
import java.util.Optional;

@Repository
public class VacationRepositoryImpl implements VacationRepository {
    @Autowired
    FileReaderAndWriter fileReaderAndWriter;

    @Value("${spring.file.name.vacations}")
    private String fileNameVacations;

    private Map<String, Vacation> vacationsMap;

    @PostConstruct
    public void init() {
        System.out.println("init: " + fileNameVacations);
        vacationsMap = findAll();
    }

    public Map<String, Vacation> findAll() {
       return fileReaderAndWriter.findAllVacations();
    }

    @Override
    public void addVacationToEmployee(Vacation vacation) {
        vacationsMap.put(vacation.getId(), vacation);
        fileReaderAndWriter.saveAllVacations(vacationsMap);
    }

    @Override
    public Optional<Vacation> removeVacation(String id) {
        Optional<Vacation> vacation = Optional.ofNullable(vacationsMap.remove(id));
        fileReaderAndWriter.saveAllVacations(vacationsMap);
        return vacation;
    }
    //  @Override
    //    public Optional<Employee> removeEmployee(String id) {
    //        Optional<Employee> employee = Optional.ofNullable(employeeMap
    //        .remove(id));
    //
    //        fileReaderAndWriter.saveAllEmployees(employeeMap);
    //        return employee;
    //    }

    @Override
    public void approveVacation(String id) {
        VacationStatus vacationStatus = VacationStatus.fromString("APPROVED");
        Vacation vacation = vacationsMap.get(id);
        if (vacation == null) {
            return;
        }
        vacation.setStatus(vacationStatus);
        fileReaderAndWriter.saveAllVacations(vacationsMap);
    }

    @Override
    public void rejectVacation(String id) {
        VacationStatus vacationStatus = VacationStatus.fromString("REJECTED");
        Vacation vacation = vacationsMap.get(id);
        if (vacation == null) {
            return;
        }
        vacation.setStatus(vacationStatus);

        removeVacation(id);
    }

    public void saveAllVacations(Map<String, Vacation> map) {
        fileReaderAndWriter.saveAllVacations(map);
    }

    @Override
    public boolean isFileEmpty(File file) throws IOException {
        return fileReaderAndWriter.isFileEmpty(file);
    }

    @Override
    public Vacation findVacation(String id) {
        return vacationsMap.get(id);
    }
}