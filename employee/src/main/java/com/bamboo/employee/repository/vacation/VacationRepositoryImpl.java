package com.bamboo.employee.repository.vacation;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.FileReaderAndWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VacationRepositoryImpl implements VacationRepository {
    @Autowired
    FileReaderAndWriter fileReaderAndWriter;

    @Value("${spring.file.name.vacations}")
    private String fileNameVacations;

    private Map<String, Vacation> vacationsMap;
    //map: (vacationId,vacation)

    @PostConstruct
    public void init() {
        System.out.println("init: " + fileNameVacations);
        vacationsMap = findAll();
    }

    public Map<String, Vacation> findAll() {
        //read from file
       return fileReaderAndWriter.findAllVacations();
    }

    @Override
    public void addVacationToEmployee(String employeeId, Vacation vacation) {
        vacationsMap.put(employeeId, vacation);
        saveAllVacations(vacationsMap);
    }

    @Override
    public void removeVacation(String id) {
        vacationsMap.remove(id);
        saveAllVacations(vacationsMap);
    }

    @Override
    public void approveVacation(String id) {
        VacationStatus vacationStatus = VacationStatus.fromString("APPROVED");
        Vacation vacation = vacationsMap.get(id);
        if (vacation == null) {
            return;
        }
        vacation.setStatus(vacationStatus);
        saveAllVacations(vacationsMap);
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
        saveAllVacations(vacationsMap);
    }

    public void saveAllVacations(Map<String, Vacation> map) {
        //map: (id,vacation)
        //write to file
        fileReaderAndWriter.saveAllVacations(map);
    }

    @Override
    public boolean isFileEmpty(File file) throws IOException {
        return fileReaderAndWriter.isFileEmpty(file);
    }
}
