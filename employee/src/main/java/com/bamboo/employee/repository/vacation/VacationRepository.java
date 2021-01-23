package com.bamboo.employee.repository.vacation;

import com.bamboo.employee.entities.Vacation;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface VacationRepository {
    void addVacationToEmployee(Vacation vacation);

    Map<String, Vacation> findAll();

    void saveAllVacations(Map<String, Vacation> map);

    void removeVacation(String id);

    void approveVacation(String id);

    void rejectVacation(String id);

    boolean isFileEmpty(File file) throws IOException;

    Vacation findVacation(String id);
}
