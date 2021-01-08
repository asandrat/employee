package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<Integer, Employee> employees = new HashMap<>();
    private final Map<VacationId, Vacation> vacations = new HashMap<>();

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
    public void create(final Vacation vacation) {
        VacationId key = vacation.getId();
        if (vacations.containsKey(key)) {
            System.out.println("Vacation with key: " + key + " already exists");
        } else {
            if (associateVacationWithEmployee(vacation)) {
                vacations.put(key, vacation);
                System.out.println("Successfully added vacation to employee");
            } else {
                System.out.println(
                        "Can't add vacation to non-existing employee");
            }
        }
    }

    @Override
    public Employee read(int employeeId) {
        return employees.get(employeeId);
    }

    @Override
    public Vacation read(VacationId vacationId) {
        return vacations.get(vacationId);
    }

    private boolean associateVacationWithEmployee(final Vacation vacation) {
        Integer employeeId = vacation.getId().getEmployeeId();
        Employee employee = employees.get(employeeId);
        if (employee == null) {
            return false;
        }

        if (employee.getVacations() == null) {
            employee.setVacations(new ArrayList<>());
        }
        employee.addVacation(vacation);
        return true;
    }

    @Override
    public void saveAll(final Collection<Employee> employees) {
        //TODO: delegate to repository to save employees
        // #TODO save in file
    }
}
