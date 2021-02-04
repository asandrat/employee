package com.bamboo.employee.repository;

import com.bamboo.employee.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@RequiredArgsConstructor
@Repository
public class EmployeeRepositoryImpl extends EntityRepository<Employee> implements EmployeeRepository {

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
