package com.bamboo.employee.service.employee;

import com.bamboo.employee.entity.FavoriteVacationEntity;
import com.bamboo.employee.exceptions.VacationNotFoundException;
import com.bamboo.employee.mapper.EmployeeMapper;
import com.bamboo.employee.mapper.FavoriteVacationMapper;
import com.bamboo.employee.mapper.VacationMapper;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.model.FavoriteVacation;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.entity.VacationEntity;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import com.bamboo.employee.exceptions.EmployeeNotFoundException;
import com.bamboo.employee.service.vacationstate.VacationStateManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final VacationStateManager vacationStateManager;

    public EmployeeServiceImpl(final EmployeeRepository repository,
                               final VacationStateManager vacationStateManager) {
        this.repository = repository;
        this.vacationStateManager = vacationStateManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAll() {
        return repository.findAll().stream()
                .map(EmployeeMapper.INSTANCE::entityToEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public Employee addEmployee(final Employee employee) {
        EmployeeEntity employeeEntity =
                EmployeeMapper.INSTANCE.employeeToEntity(employee);
        EmployeeEntity persistedEntity = repository.create(employeeEntity);
        return EmployeeMapper.INSTANCE.entityToEmployee(persistedEntity);
    }

    @Override
    @Transactional(
            readOnly = true,
            rollbackFor = EmployeeNotFoundException.class //todo jel treba ovo?
    )
    public Employee getEmployee(final int id) {
        EmployeeEntity employeeEntity = repository.read(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return EmployeeMapper.INSTANCE.entityToEmployee(employeeEntity);
    }

    @Override
    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    public Employee removeEmployee(int id) {
        Optional<EmployeeEntity> maybeEmployeeEntity = repository.delete(id);
        if (!maybeEmployeeEntity.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }
        return EmployeeMapper.INSTANCE.entityToEmployee(maybeEmployeeEntity.get());
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
            throw new EmployeeNotFoundException(employeeId);
        }

        optionalEmployeeEntity.get()
                .addVacation(VacationMapper.INSTANCE.vacationToEntity(vacation));

        return vacation;
    }

    @Override
    @Transactional(readOnly = true)
    public Vacation getVacationFromEmployee(final VacationId vacationId) {
        Employee e = this.getEmployee(vacationId.getEmployeeId());
        return e.getVacation(vacationId.getUniqueId()).orElseThrow(() -> new VacationNotFoundException(vacationId));
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
        return VacationMapper.INSTANCE.entityToVacation(vacationEntity.get());
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
    public void removeVacationFromEmployee(final int employeeId,
                                          final int vacationId) {
        EmployeeEntity employeeEntity = repository.read(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        VacationEntity vacationEntity = repository.findEmployeesVacationById(employeeId, vacationId)
                .orElseThrow(() -> new VacationNotFoundException(employeeId, vacationId));

        employeeEntity.removeVacation(vacationEntity);
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
    public void updateVacationForEmployee(final int employeeId,
                                         final int vacationId,
                                         final VacationStatus target) {
        VacationEntity vacationEntity = repository.findEmployeesVacationById(employeeId, vacationId)
                .orElseThrow(() -> new VacationNotFoundException(employeeId, vacationId));

        vacationEntity.setStatus(vacationStateManager.getValidStatus(vacationEntity.getStatus(), target));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Vacation> findAllEmployeesVacations(final int employeeId) {
        return repository.findAllEmployeesVacations(employeeId).stream()
                .map(VacationMapper.INSTANCE::entityToVacation)
                .collect(Collectors.toList());
    }

    @Override
    public void createEmployeesFavoriteVacation(final FavoriteVacation favoriteVacation) {
        FavoriteVacationEntity favoriteVacationEntity =
                FavoriteVacationMapper.INSTANCE
                        .favoriteVacationToEntity(favoriteVacation);
        repository.createEmployeesFavoriteVacation(favoriteVacationEntity);
    }

    @Override
    public List<Employee> findFirstNEmployeesByTimestamp(
            final int maxNumberOfEmployeesPerTask,
            final Timestamp timestamp) {
        return repository.findFirstNEmployeesByTimestamp(maxNumberOfEmployeesPerTask, timestamp)
                .stream()
                .map(EmployeeMapper.INSTANCE::entityToEmployee)
                .collect(Collectors.toList());

    }

    @Override
    public void removeEmployeesFavoriteVacations(final int uniqueId) {
        repository.deleteEmployeesFavoriteVacations(uniqueId);
    }
}
