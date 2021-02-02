package com.bamboo.employee.repositoryDB.employee;

import com.bamboo.employee.entitiesDB.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class EmployeeRepositoryDBImpl implements EmployeeRepositoryDB {

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
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public Employee findEmployeeById(long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public void deleteEmployeeById(long id) {
        Employee employee = findEmployeeById(id);
        entityManager.remove(employee);
        entityManager.flush();
        entityManager.clear();
    }
}
