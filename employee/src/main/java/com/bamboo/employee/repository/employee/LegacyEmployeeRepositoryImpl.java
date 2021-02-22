package com.bamboo.employee.repository.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import org.springframework.stereotype.Repository;

import java.util.*;


//@Repository
public class EmployeeRepositoryImpl {
//
//    private final Map<Integer, Employee> employees = new HashMap<>();
//
//    @Override
//    public Collection<Employee> findAll() {
//        return employees.values();
//    }
//
//
//    @Override
//    public Employee create(final Employee employee) {
//        int key = employee.getUniqueId();
//        employees.put(key, employee);
//        return employees.get(key);
//    }
//
//    @Override
//    public Optional<Employee> read(final int employeeId) {
//        return Optional.ofNullable(employees.get(employeeId));
//    }
//
//    @Override
//    public void saveAll(final Collection<Employee> employees) {
//        // #todo: delegate to repository to save employees
//        // #todo save in file
//    }
//
//    @Override
//    public Vacation addVacationToEmployee(Vacation vacation) {
//        int employeeId = vacation.getId().getEmployeeId();
//        Employee employee = employees.get(employeeId);
//        return employee.addVacation(vacation);
//    }
//
//    @Override
//    public Optional<Employee> delete(final int id) {
//        return Optional.ofNullable(employees.remove(id));
//    }
//
//    @Override
//    public Vacation deleteVacation(final VacationId id) {
//        return employees.get(id.getEmployeeId()).removeVacation(id);
//    }
//
//    @Override
//    public void update(final VacationId vacationId,
//                       final VacationStatus status) {
//        Employee e = employees.get(vacationId.getEmployeeId());
//        e.updateVacation(vacationId, status);
//    }
}
