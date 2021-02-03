package com.bamboo.employee.service.employee;

import com.bamboo.employee.exceptions.InvalidStateTransitionException;
import com.bamboo.employee.exceptions.VacationNotFoundException;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.EmployeeEntity;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationEntity;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import com.bamboo.employee.exceptions.EmployeeNotFoundException;
import com.bamboo.employee.service.vacationstate.VacationStateManager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final VacationStateManager vacationStateManager;
    private final ModelMapper mapper;

    public EmployeeServiceImpl(final EmployeeRepository repository,
                               final VacationStateManager vacationStateManager,
                               final ModelMapper mapper) {
        this.repository = repository;
        this.vacationStateManager = vacationStateManager;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAll() {
        return repository.findAll().stream()
                .map(employeeEntity -> mapper.map(employeeEntity, Employee.class))
                .collect(Collectors.toList());
    }

    @Override
    public Employee addEmployee(final Employee employee) {
        EmployeeEntity employeeEntity = mapper.map(employee,
                EmployeeEntity.class);
        return mapper.map(repository.create(employeeEntity), Employee.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployee(final int id) {
        EmployeeEntity employeeEntity = repository.read(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return mapper.map(employeeEntity, Employee.class);
    }

    @Override
    @Transactional
    public Employee removeEmployee(int id) {
        Optional<EmployeeEntity> maybeEmployeeEntity = repository.delete(id);
        if (!maybeEmployeeEntity.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }
        return mapper.map(maybeEmployeeEntity.get(), Employee.class);
    }

    @Override
    public Vacation addVacationToEmployee(final Vacation vacation) {
//        Optional<Employee> maybeEmployee =
//        repository.read(vacation.getId().getEmployeeId());
//
//        if (!maybeEmployee.isPresent()) {
//            throw new IllegalArgumentException("No such employee to associate" +
//                    " vacation with");
//        }
//
//        Optional<Vacation> maybeVacation =
//                maybeEmployee.get().getVacation(vacation.getId());
//        if (maybeVacation.isPresent()) {
//            throw new IllegalArgumentException("Employee already contains "
//                    + "vacation with id: " + vacation.getId().getUniqueId());
//        }
//        return repository.addVacationToEmployee(vacation);
        return null;
    }

    @Override
    public Vacation addVacationToEmployee(final int employeeId,
                                          final Vacation vacation) {
        Optional<EmployeeEntity> optionalEmployeeEntity = repository.read(employeeId);
        if (!optionalEmployeeEntity.isPresent()) {
            throw new IllegalArgumentException(
                    "No such employee " + employeeId + " to associate"
                    + " vacation with");
        }

        VacationEntity vacationEntity = repository.addVacationToEmployee(
                optionalEmployeeEntity.get(),
                mapper.map(vacation, VacationEntity.class));
        return mapper.map(vacationEntity, Vacation.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Vacation getVacationFromEmployee(final VacationId vacationId) {
        Employee e = this.getEmployee(vacationId.getEmployeeId());
        return e.getVacation(vacationId).orElseThrow(() -> new VacationNotFoundException(vacationId));
    }

    @Override
    @Transactional(readOnly = true)
    public Vacation getVacationFromEmployee(final int employeeId,
                                            final int vacationId) {
        Optional<VacationEntity> vacationEntity =
                repository.findEmployeesVacationById(employeeId, vacationId);
        if (!vacationEntity.isPresent()) {
            throw new VacationNotFoundException(employeeId, vacationId);
        }
        return mapper.map(vacationEntity.get(), Vacation.class);
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
    public int removeVacationFromEmployee(final int employeeId,
                                          final int vacationId) {
        return repository.deleteVacation(employeeId, vacationId);
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

    @Override
    public int updateVacationForEmployee(final int employeeId,
                                         final int vacationId,
                                         final VacationStatus target) {
        Vacation vacation = getVacationFromEmployee(employeeId, vacationId);
        return repository.update(employeeId, vacationId,
                vacationStateManager.getValidStatus(vacation.getStatus(), target));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Vacation> findAllEmployeesVacations(final int employeeId) {
        return repository.findAllEmployeesVacations(employeeId).stream()
                .map(vacationEntity -> mapper.map(vacationEntity, Vacation.class))
                .collect(Collectors.toList());
    }
}
