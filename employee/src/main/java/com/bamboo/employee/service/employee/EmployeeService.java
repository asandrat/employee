package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;

public interface EmployeeService {
    boolean addEmployee(Employee employee);

    Employee getEmployee(int id);

    Employee removeEmployee(Integer id);

    void addVacationToEmployee(Vacation vacation);

    Vacation removeVacationFromEmployee(VacationId id);

    boolean approveVacationForEmployee(VacationId vacationId);

    boolean rejectVacationForEmployee(VacationId vacationId);
}
