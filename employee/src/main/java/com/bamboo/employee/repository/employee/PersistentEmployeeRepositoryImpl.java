package com.bamboo.employee.repository.employee;

import com.bamboo.employee.entity.FavoriteVacationEntity;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.entity.VacationEntity;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class PersistentEmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public PersistentEmployeeRepositoryImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public @NotNull List<EmployeeEntity> findAll() {
        Query query = entityManager.createQuery(
                "from EmployeeEntity",
                EmployeeEntity.class);
        return (List<EmployeeEntity>)query.getResultList();
    }

    @Override
    public Optional<EmployeeEntity> read(final int employeeId) {
        return Optional.ofNullable(
                entityManager.find(EmployeeEntity.class, employeeId));
    }

    @Override
    public EmployeeEntity create(final EmployeeEntity employeeEntity) {
        entityManager.persist(employeeEntity);
        entityManager.flush();
        return employeeEntity;
    }

    @Override
    public void saveAll(final Collection<Employee> employees) {

    }

    @Override
    public Optional<EmployeeEntity> delete(final int id) {
        Optional<EmployeeEntity> employeeEntity = read(id);
        employeeEntity.ifPresent(entityManager::remove);
        return employeeEntity;
    }

    @Override
    public Vacation deleteVacation(final VacationId id) {
        return null;
    }

    @Override
    public void update(final VacationId vacationId, final VacationStatus status) {
        // legacy method
    }

    @Override
    public Collection<VacationEntity> findAllEmployeesVacations(final int employeeId) {
        Query query = entityManager.createQuery(
                "from VacationEntity where employee_id = :employeeId",
                VacationEntity.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

    @Override
    public Optional<VacationEntity> findEmployeesVacationById(final int employeeId,
                                                    final int vacationId) {
        Optional<EmployeeEntity> employeeEntity = this.read(employeeId);
        return employeeEntity.flatMap(
                entity -> entity.getVacations().stream()
                        .filter(vacationEntity -> vacationEntity.getId() == vacationId)
                        .findFirst());
    }

    @Override
    public List<EmployeeEntity> findFirstNEmployeesByTimestamp(
            final int maxNumberOfEmployees,
            final Timestamp timestamp) {
        Query query = entityManager.createQuery(
                "from EmployeeEntity where creationTime > " +
                        ":minCreationTime");
        query.setParameter("minCreationTime", timestamp);
        query.setMaxResults(maxNumberOfEmployees);
        return query.getResultList();
    }

    @Override
    public void createEmployeesFavoriteVacation(
            final FavoriteVacationEntity favoriteVacationEntity) {
        entityManager.merge(favoriteVacationEntity);
    }

    @Override
    public void deleteEmployeesFavoriteVacations(final int uniqueId) {
        Query query = entityManager.createQuery(
                "delete from FavoriteVacationEntity where "
                        + "employeeId = :uniqueId");
        query.setParameter("uniqueId", uniqueId);
        int x = query.executeUpdate();
    }
}
