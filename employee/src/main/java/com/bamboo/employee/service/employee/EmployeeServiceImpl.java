package com.bamboo.employee.service.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addEmployee(final Employee employee) {
        return repository.create(employee);
    }

    @Override
    public Employee getEmployee(final int id) {
        return repository.read(id);
    }

    @Override
    public Employee removeEmployee(Integer id) {
        return repository.delete(id);
    }

    @Override
    public void addVacationToEmployee(final Vacation vacation) {
        Employee employee = repository.read(vacation.getId().getEmployeeId());
        if (employee == null) {
            throw new IllegalArgumentException("No such employee to associate vacation with");
        }
        if (employee.getVacation(vacation.getId()) != null) {
            throw new IllegalArgumentException("Employee already contains "
                    + "vacation with id: " + vacation.getId());
        }
        repository.addVacationToEmployee(vacation);
    }

    @Override
    public Vacation removeVacationFromEmployee(final VacationId id) {
        return repository.deleteVacation(id);
    }

    @Override
    public boolean approveVacation(final VacationId vacationId) {
        VacationStatus status = repository.read(vacationId.getEmployeeId())
                .getVacation(vacationId)
                .getStatus();

        switch (status) {
            case APPROVED:
                System.err.println("Can't approve already approved vacation");
                return false;
            case REJECTED:
                System.err.println("Can't approve rejected vacation");
                return false;
            default:
                repository.update(vacationId, VacationStatus.APPROVED);
                return true;
        }
    }

    @Override
    public boolean rejectVacation(final VacationId vacationId) {
        VacationStatus status = repository.read(vacationId.getEmployeeId())
                .getVacation(vacationId)
                .getStatus();

        switch (status) {
            case APPROVED:
                System.err.println("Can't reject already approved vacation");
                return false;
            case REJECTED:
                System.err.println("Can't reject already rejected vacation");
                return false;
            default:
                repository.update(vacationId, VacationStatus.REJECTED);
                return true;
        }
    }

}
