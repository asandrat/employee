package com.bamboo.employee.repository;


import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.entity.VacationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class VacationRepositoryImpl extends EntityRepository<Vacation> implements VacationRepository {

    @Override
    public List<Vacation> findAll(Employee employee) {
        return employee.getVacations();
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
