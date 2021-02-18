package com.bamboo.employee.repository;

import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.EmployeesFavoriteMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class EmployeeRepositoryImpl extends EntityRepository<Employee> implements EmployeeRepository {

    private volatile LocalDateTime registered = null;

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
    public List<Employee> findOldest3() {
        Query theQuery = entityManager.createQuery(
                "from Employee e left join fetch e.vacations " +
                        "where :param = null or e.registeredAt > :param " +
                        "order by e.registeredAt"
        );
        theQuery.setParameter("param", registered);
        List<Employee> employees = theQuery.setMaxResults(3).getResultList();
        registered = employees.stream()
                .map(Employee::getRegisteredAt)
                .max(LocalDateTime::compareTo)
                .get();
        if (employees.size() < 3) {
            registered = null;
        }
        return employees;
    }

    @Override
    @Transactional
    public void saveFavoriteMonth(EmployeesFavoriteMonth employeesFavoriteMonth) {
        entityManager.persist(employeesFavoriteMonth);
    }

}
