package com.bamboo.employee.repositoryDB.vacation;

import com.bamboo.employee.entitiesDB.Vacation;

import java.util.Collection;

public interface VacationRepositoryDB {

    Collection<Vacation> findAllVacations();

    void addVacationToEmployee(Vacation vacation);

    Vacation findVacationById (long id);

    void deleteVacation(Vacation vacation);

    void approveVacation(Vacation vacation);

    void rejectVacation(Vacation vacation);

}
