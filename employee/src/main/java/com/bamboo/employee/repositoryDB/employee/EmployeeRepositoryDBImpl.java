package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.EmployeesFavouriteMonth;
import com.bamboo.employee.entitiesDB.Vacation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public class EmployeeRepositoryDBImpl implements EmployeeRepositoryDB {

    @PersistenceContext
    private final EntityManager entityManager;

    public EmployeeRepositoryDBImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<Employee> findAllEmployees() {
        return entityManager.createQuery("from Employee", Employee.class)
                .getResultList();
    }

    @Override
    public void addEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public Employee findEmployeeById(long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        entityManager.remove(employee);
    }

    @Override
    public Collection<Vacation> findAllVacationsOfEmployee(long id) {
        return entityManager.createQuery(
                "from Vacation where employee_id=:id", Vacation.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Employee> findFirstNRegisteredEmployees(int n, LocalDate registered) {
        Query query = entityManager.createQuery(
                "Select e from Employee as e join fetch e.vacations " +
                        "where :param = null or e.registrationDate > :param " +
                        "order by e.registrationDate asc");
        query.setParameter("param", registered);

        return (List<Employee>) query.setMaxResults(n).getResultList();
    }

    @Override
    public void saveFavoriteMonth(EmployeesFavouriteMonth employeesFavoriteMonth) {
        entityManager.persist(employeesFavoriteMonth);
    }

}
