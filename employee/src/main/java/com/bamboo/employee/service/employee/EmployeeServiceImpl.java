package com.bamboo.employee.service.employee;

import com.bamboo.employee.exceptions.VacationNotFoundException;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import com.bamboo.employee.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean addEmployee(final Employee employee) {
        if (repository.read(employee.getUniqueId()).isPresent()) {
            return false;
        }
        return repository.create(employee);
    }

    @Override
    public Employee getEmployee(final int id) {
        return repository.read(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public Employee removeEmployee(int id) {
        return repository.delete(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public void addVacationToEmployee(final Vacation vacation) {
        Optional<Employee> maybeEmployee =
                repository.read(vacation.getId().getEmployeeId());

        if (!maybeEmployee.isPresent()) {
            throw new IllegalArgumentException("No such employee to associate vacation with");
        }

        Optional<Vacation> maybeVacation =
                maybeEmployee.get().getVacation(vacation.getId());
        if (maybeVacation.isPresent()) {
            throw new IllegalArgumentException("Employee already contains "
                    + "vacation with id: " + vacation.getId().getUniqueId());
        }
        repository.addVacationToEmployee(vacation);
    }

    @Override
    public Vacation getVacationFromEmployee(final VacationId vacationId) {
        Employee e = this.getEmployee(vacationId.getEmployeeId());
        return e.getVacation(vacationId).orElseThrow(() -> new VacationNotFoundException(vacationId));
    }

    @Override
    public Vacation removeVacationFromEmployee(final VacationId id) {
        Vacation v =  repository.deleteVacation(id);

        if (v != null) {
            System.out.println("Successfully removed vacation with id: " + id);
        } else {
            System.out.println("Failed to remove vacation with id: " + id);
        }
        return v;
    }

    @Override
    public boolean approveVacationForEmployee(final VacationId vacationId) {
        VacationStatus status = repository.read(vacationId.getEmployeeId())
                .get()
                .getVacation(vacationId)
                .get()
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
    public boolean rejectVacationForEmployee(final VacationId vacationId) {
        VacationStatus status = repository.read(vacationId.getEmployeeId())
                .get()
                .getVacation(vacationId)
                .get()
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
