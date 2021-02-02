package com.bamboo.employee.repositoryFile.vacation;

import com.bamboo.employee.entitiesFile.VacationFile;
import com.bamboo.employee.entitiesFile.VacationStatusFile;
import com.bamboo.employee.repositoryFile.FileReaderAndWriter;
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

    private Map<String, VacationFile> vacationsMap;

    @PostConstruct
    public void init() {
        System.out.println("init: " + fileNameVacations);
        vacationsMap = findAll();
    }

    public Map<String, VacationFile> findAll() {
       return fileReaderAndWriter.findAllVacations();
    }

    @Override
    public void addVacationToEmployee(VacationFile vacation) {
        vacationsMap.put(vacation.getId(), vacation);
        fileReaderAndWriter.saveAllVacations(vacationsMap);
    }

    @Override
    public Optional<VacationFile> removeVacation(String id) {
        Optional<VacationFile> vacation = Optional.ofNullable(vacationsMap.remove(id));
        fileReaderAndWriter.saveAllVacations(vacationsMap);
        return vacation;
    }
    @Override
    public void approveVacation(String id) {
        VacationStatusFile vacationStatus = VacationStatusFile.fromString("APPROVED");
        VacationFile vacation = vacationsMap.get(id);
        if (vacation == null) {
            return;
        }
        vacation.setStatus(vacationStatus);
        fileReaderAndWriter.saveAllVacations(vacationsMap);
    }

    @Override
    public void rejectVacation(String id) {
        VacationStatusFile vacationStatus = VacationStatusFile.fromString("REJECTED");
        VacationFile vacation = vacationsMap.get(id);
        if (vacation == null) {
            return;
        }
        vacation.setStatus(vacationStatus);

        removeVacation(id);
    }

    public void saveAllVacations(Map<String, VacationFile> map) {
        fileReaderAndWriter.saveAllVacations(map);
    }

    @Override
    public boolean isFileEmpty(File file) throws IOException {
        return fileReaderAndWriter.isFileEmpty(file);
    }

    @Override
    public VacationFile findVacation(String id) {
        return vacationsMap.get(id);
    }
}
