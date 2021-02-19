package com.bamboo.employee.repository;

import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.EmployeesFavoriteMonth;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<Employee> findOldestRegisteredEmployees(
            int limit,
            LocalDateTime registeredFrom
    ) {
        Query theQuery = entityManager.createQuery(
                "from Employee e join fetch e.vacations " +
                        "where :param = null or e.registeredAt > :param " +
                        "order by e.registeredAt"
        );
        theQuery.setParameter("param", registeredFrom);
        List<Employee> employees = theQuery.setMaxResults(limit).getResultList();

        return employees;
    }

    @Override
    @Transactional
    public void saveFavoriteMonth(EmployeesFavoriteMonth employeesFavoriteMonth) {
        entityManager.persist(employeesFavoriteMonth);
    }

}
