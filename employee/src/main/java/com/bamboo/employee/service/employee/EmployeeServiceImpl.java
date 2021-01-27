package com.bamboo.employee.service.employee;

import com.bamboo.employee.exceptions.InvalidStateTransitionException;
import com.bamboo.employee.exceptions.VacationNotFoundException;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import com.bamboo.employee.exceptions.EmployeeNotFoundException;
import com.bamboo.employee.service.vacationstate.VacationStateManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final VacationStateManager vacationStateManager;


    public EmployeeServiceImpl(final EmployeeRepository repository,
                               final VacationStateManager stateManager) {
        this.repository = repository;
        this.vacationStateManager = stateManager;
    }

    @Override
    public Collection<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee addEmployee(final Employee employee) {
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
    public Vacation addVacationToEmployee(final Vacation vacation) {
        Optional<Employee> maybeEmployee =
                repository.read(vacation.getId().getEmployeeId());

        if (!maybeEmployee.isPresent()) {
            throw new IllegalArgumentException("No such employee to associate" +
                    " vacation with");
        }

        Optional<Vacation> maybeVacation =
                maybeEmployee.get().getVacation(vacation.getId());
        if (maybeVacation.isPresent()) {
            throw new IllegalArgumentException("Employee already contains "
                    + "vacation with id: " + vacation.getId().getUniqueId());
        }
        return repository.addVacationToEmployee(vacation);
    }

    @Override
    public Vacation getVacationFromEmployee(final VacationId vacationId) {
        Employee e = this.getEmployee(vacationId.getEmployeeId());
        return e.getVacation(vacationId).orElseThrow(() -> new VacationNotFoundException(vacationId));
    }

    @Override
    public Vacation removeVacationFromEmployee(final VacationId id) {
        Vacation v = repository.deleteVacation(id);

        //todo zamentiti sout sa Logger-om
        if (v != null) {
            System.out.println("Successfully removed vacation with id: " + id);
        } else {
            System.out.println("Failed to remove vacation with id: " + id);
        }
        return v;
    }

    @Override
    public Vacation updateVacationForEmployee(final VacationId id,
                                              final VacationStatus target) {
        Vacation v = getVacationFromEmployee(id);
        VacationStatus status = v.getStatus();
        repository.update(id, vacationStateManager.getValidStatus(status,
                target));
        return v;
    }
}
