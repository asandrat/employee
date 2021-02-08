package com.bamboo.employee.repositoryDB.vacation;

import com.bamboo.employee.entitiesDB.Vacation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class VacationRepositoryDBImpl implements VacationRepositoryDB {

    @PersistenceContext
    private final EntityManager entityManager;

    public VacationRepositoryDBImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<Vacation> findAllVacations() {
        return entityManager.createQuery("from Vacation", Vacation.class)
                .getResultList();
    }

    @Override
    public void addVacationToEmployee(Vacation vacation) {
        entityManager.persist(vacation);
    }

    @Override
    public void deleteVacation(Vacation vacation) {
        entityManager.remove(vacation);
    }

    @Override
    public Vacation findVacationById(long id) {
        return entityManager.find(Vacation.class, id);
    }

    @Override
    public void approveVacation(Vacation vacation) {
        if (vacation == null) {
            throw new IllegalArgumentException("Vacation not found.");
        }
        Query query = entityManager.createQuery("update Vacation v " +
                "set v.status = 'APPROVED' " +
                "where v.id = :id");
        long id = vacation.getId();
        query.setParameter("id", id);
        query.executeUpdate();
        System.out.println("vacation approved");
    }

    @Override
    public void rejectVacation(Vacation vacation) {
        if (vacation == null) {
            throw new IllegalArgumentException("Vacation not found.");
        }
        Query query = entityManager.createQuery("update Vacation v " +
                "set v.status = 'REJECTED' " +
                "where v.id = :id");
        long id = vacation.getId();
        query.setParameter("id", id);
        query.executeUpdate();
        deleteVacation(vacation);
        System.out.println("vacation rejected and deleted.");
    }
}
