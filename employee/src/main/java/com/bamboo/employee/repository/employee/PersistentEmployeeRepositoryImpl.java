package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.entity.VacationEntity;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Optional;

@Repository
public class PersistentEmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public @NotNull Collection<EmployeeEntity> findAll() {
        Query query = entityManager.createQuery(
                "from EmployeeEntity",
                EmployeeEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<EmployeeEntity> read(final int employeeId) {
        return Optional.ofNullable(
                entityManager.find(EmployeeEntity.class, employeeId));
    }

    @Override
    public EmployeeEntity create(final EmployeeEntity employeeEntity) {
        return entityManager.merge(employeeEntity);
    }

    @Override
    public void saveAll(final Collection<Employee> employees) {

    }

    @Override
    public VacationEntity addVacationToEmployee(final EmployeeEntity employeeEntity,
                                                final VacationEntity vacation) {
        employeeEntity.addVacation(vacation);
        vacation.setEmployee(employeeEntity);
        // ili persist ?
        return entityManager.merge(vacation);
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
        Query query = entityManager.createQuery("From VacationEntity where " +
                "employee_id = :employeeId and id = :vacationId",
                VacationEntity.class);
        query.setParameter("employeeId", employeeId);
        query.setParameter("vacationId", vacationId);
        return query.getResultList().stream().findFirst();
    }
}
