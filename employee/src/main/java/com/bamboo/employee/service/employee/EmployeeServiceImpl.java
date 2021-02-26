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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final VacationStateManager vacationStateManager;
    private final EmployeeMapper employeeMapper;
    private final VacationMapper vacationMapper;
    private final FavoriteVacationMapper favoriteVacationMapper;

    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAllEmployees() {
        return repository.findAll().stream()
                .map(employeeMapper::entityToEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public Employee addEmployee(final Employee employee) {
        EmployeeEntity employeeEntity =
                employeeMapper.employeeToEntity(employee);
        EmployeeEntity persistedEntity = repository.create(employeeEntity);
        return employeeMapper.entityToEmployee(persistedEntity);
    }

    @Override
    @Transactional(
            readOnly = true,
            rollbackFor = EmployeeNotFoundException.class //todo jel treba ovo?
    )
    public Employee getEmployee(final int id) {
        EmployeeEntity employeeEntity = repository.read(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return employeeMapper.entityToEmployee(employeeEntity);
    }

    @Override
    @Transactional(rollbackFor = EmployeeNotFoundException.class)
    public Employee removeEmployee(int id) {
        Optional<EmployeeEntity> maybeEmployeeEntity = repository.delete(id);
        if (!maybeEmployeeEntity.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }
        return employeeMapper.entityToEmployee(maybeEmployeeEntity.get());
    }


    @Override
    public Vacation addVacationToEmployee(final int employeeId,
                                          final Vacation vacation) {
        Optional<EmployeeEntity> optionalEmployeeEntity = repository.read(employeeId);
        if (!optionalEmployeeEntity.isPresent()) {
            throw new EmployeeNotFoundException(employeeId);
        }

        optionalEmployeeEntity.get()
                .addVacation(vacationMapper.vacationToEntity(vacation));

        return vacation;
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
        return vacationMapper.entityToVacation(vacationEntity.get());
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
                .map(vacationMapper::entityToVacation)
                .collect(Collectors.toList());
    }

    @Override
    public void createEmployeesFavoriteVacation(final Employee employee,
                                                final FavoriteVacation favoriteVacation) {
        FavoriteVacationEntity favoriteVacationEntity =
                        favoriteVacationMapper.favoriteVacationToEntity(favoriteVacation);
        favoriteVacationEntity.setEmployeeEntity(employeeMapper.employeeToEntity(employee));
        repository.createEmployeesFavoriteVacation(favoriteVacationEntity);
    }

    @Override
    public List<Employee> findFirstNEmployeesByTimestamp(
            final int maxNumberOfEmployeesPerTask,
            final Timestamp timestamp) {
        return repository.findFirstNEmployeesByTimestamp(maxNumberOfEmployeesPerTask, timestamp)
                .stream()
                .map(employeeMapper::entityToEmployee)
                .collect(Collectors.toList());

    }

    @Override
    public void removeEmployeesFavoriteVacations(final int uniqueId) {
        repository.deleteEmployeesFavoriteVacations(uniqueId);
    }
}
