package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VacationRepositoryImpl implements VacationRepository {

    private final EmployeeRepository employeeRepository;

    private final Map<VacationId, Vacation> vacations = new HashMap<>();

    @Autowired
    public VacationRepositoryImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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

    private boolean associateVacationWithEmployee(final Vacation vacation) {
        Integer employeeId = vacation.getId().getEmployeeId();
        Employee employee = employeeRepository.read(employeeId);
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
    public Vacation read(final VacationId vacationId) {
        return vacations.get(vacationId);
    }
}
