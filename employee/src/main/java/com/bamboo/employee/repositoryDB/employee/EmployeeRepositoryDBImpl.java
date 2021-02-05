package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.Vacation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

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
        System.out.println(employee);
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

}
