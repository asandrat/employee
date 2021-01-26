package com.bamboo.employee.repository;

import com.bamboo.employee.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EntityManager entityManager;

    @Override
    public List<Employee> findAll() {
        Query theQuery = entityManager.createQuery("from Employee");
        return (List<Employee>) theQuery.getResultList();
    }

    @Override
    public Employee findById(int theId) {
        return entityManager.find(Employee.class, theId);
    }

    @Override
    public void save(Employee employee) {
        Employee dbEmployee =  entityManager.merge(employee);
        employee.setUniqueId(dbEmployee.getUniqueId());
    }

    @Override
    public void deleteById(int theId) {
        Employee employee = entityManager.getReference(Employee.class, theId);
        Query theQuery = entityManager.createQuery(
                "delete from Vacation where employee.id=:employeeId"
        );
        theQuery.setParameter("employeeId", theId);
        theQuery.executeUpdate();
        entityManager.remove(employee);
    }
}
