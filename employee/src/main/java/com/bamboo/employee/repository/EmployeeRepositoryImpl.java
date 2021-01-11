package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public void create(final Employee employee) {
        Integer key = employee.getUniqueId();
        if (employees.containsKey(key)) {
            System.out.println("Employee with key: " + key + " already exists");
        } else {
            System.out.println("Successfully added employee");
            employees.put(key, employee);
        }
    }

    @Override
    public Employee read(final int employeeId) {
        return employees.get(employeeId);
    }

    @Override
    public void saveAll(final Collection<Employee> employees) {
        // #todo: delegate to repository to save employees
        // #todo save in file
    }

    @Override
    public void delete(Integer id) {
        Employee e = employees.remove(id);
        if (e == null) {
            System.out.println("There's no such employee with id: " + id);
        } else {
            System.out.println("Successfully removed employee");
        }
    }
}
