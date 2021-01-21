package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<Integer, Employee> employees = new HashMap<>();

    @Override
    public Collection<Employee> findAll() {
        return employees.values();
    }


    @Override
    public boolean create(final Employee employee) {
        Integer key = employee.getUniqueId();
        if (employees.containsKey(key)) {
            System.out.println("Employee with key: " + key + " already exists");
            return false;
        } else {
            System.out.println("Successfully added employee");
            employees.put(key, employee);
            return true;
        }
    }

    @Override
    public Employee read(final int employeeId) {
        if (employees.containsKey(employeeId)) {
            return employees.get(employeeId);
        }
        throw new IllegalArgumentException("No such employee with id: " + employeeId);
    }

    @Override
    public void saveAll(final Collection<Employee> employees) {
        // #todo: delegate to repository to save employees
        // #todo save in file
    }

    @Override
    public void addVacationToEmployee(Vacation vacation) {
        Integer employeeId = vacation.getId().getEmployeeId();
        Employee employee = employees.get(employeeId);
        employee.addVacation(vacation);
    }

    @Override
    public Employee delete(Integer id) {
        return employees.remove(id);
    }

    @Override
    public Vacation deleteVacation(final VacationId id) {
        Vacation v = employees.get(id.getEmployeeId()).removeVacation(id);
        if (v != null) {
            System.out.println("Successfully removed vacation with id: " + id);
        } else {
            System.out.println("Failed to remove vacation with id: " + id);
        }
        return v;
    }

    @Override
    public void update(final VacationId vacationId,
                       final VacationStatus status) {
        Employee e = employees.get(vacationId.getEmployeeId());
        e.updateVacation(vacationId, status);
    }
}
