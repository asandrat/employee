package com.bamboo.employee.repositoryFile.vacation;

import com.bamboo.employee.entitiesFile.VacationFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface VacationRepository {
    void addVacationToEmployee(VacationFile vacation);

    Map<String, VacationFile> findAll();

    void saveAllVacations(Map<String, VacationFile> map);

    Optional<VacationFile> removeVacation(String id);

    void approveVacation(String id);

    void rejectVacation(String id);

    boolean isFileEmpty(File file) throws IOException;

    VacationFile findVacation(String id);
}
