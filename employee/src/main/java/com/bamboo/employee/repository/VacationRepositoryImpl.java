package com.bamboo.employee.repository;


import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.entity.VacationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class VacationRepositoryImpl implements VacationRepository {

    private final EntityManager entityManager;

    @Override
    public Vacation save(Employee employee, Vacation vacation) {
        employee.addVacation(vacation);
        entityManager.persist(vacation);
        return vacation;
    }

    @Override
    public Vacation findById(int vacationId) {
        return entityManager.find(Vacation.class, vacationId);
    }

    @Override
    public List<Vacation> findAll(Employee employee) {
        return employee.getVacations();
    }

    @Override
    public void deleteById(int vacationId) {
        Query theQuery = entityManager.createQuery(
                "delete from Vacation where id = :vacationId"
        );
        theQuery.setParameter("vacationId", vacationId);
        theQuery.executeUpdate();
    }

    @Override
    public void changeVacationStatus(Vacation vacation, VacationStatus status) {
        Query theQuery = entityManager.createQuery(
                "update Vacation v set v.vacationStatus = :status " +
                        "where v.uniqueId = :vacationId"
        );
        theQuery.setParameter("vacationId", vacation.getUniqueId());
        theQuery.setParameter("status", status);
        theQuery.executeUpdate();
    }
}
