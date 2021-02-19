package com.bamboo.employee.service;

import com.bamboo.employee.custom.exception.EmployeeNotFoundException;
import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.EmployeesFavoriteMonth;
import com.bamboo.employee.mapstruct.EmployeeMapper;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.multithreading.CronJobProcessor;
import com.bamboo.employee.repository.EmployeeRepository;
import com.bamboo.employee.repository.EntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EntityRepository<Employee> dao;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private LocalDateTime registeredFrom = null;
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Autowired
    public void setDao(EntityRepository<Employee> daoToSet) {
        dao = daoToSet;
        dao.setEntityType(Employee.class);
    }

    @Override
    @Transactional
    public EmployeeDTO addEmployee(String name, String surname) {
        Employee employee = new Employee(name, surname);
        employee.setRegisteredAt(LocalDateTime.now());
        Employee dbEmployee = employeeRepository.save(employee);
        return mapper.employeeToEmployeeDTO(dbEmployee);
    }

    @Override
    @Transactional
    public void removeEmployee(int employeeId) {
        checkIfEmployeeExists(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    @Override
    @Transactional
    public List<EmployeeDTO> findAllEmployees() {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.findAll());
        return mapper.map(employeeList);
    }

    @Override
    @Transactional
    public EmployeeDTO getEmployee(int employeeId) {
        Employee employee = checkIfEmployeeExists(employeeId);
        return mapper.employeeToEmployeeDTO(employee);
    }

    public Employee checkIfEmployeeExists(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Could not find employee with id: " + employeeId
            );
        }
        return employee;
    }

    @Override
    @Transactional
    public void calculateFavoriteMonthsForEmployees(int limitNumberOfEmployees) {
        List<Employee> employees = employeeRepository.findOldestRegisteredEmployees(
                limitNumberOfEmployees,
                registeredFrom
        );
        List<Integer> favoriteMonthsForEmployees = new ArrayList<>();

        updateRegisteredFrom(employees, limitNumberOfEmployees);

        for (Employee employee : employees) {
            Future<List<Integer>> future = executorService.submit(
                    new CronJobProcessor(this, employee)
            );

            try {
                favoriteMonthsForEmployees.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (employees.size() >= 2) {
            Set<Integer> commonMonths = calculateCommonMonths(
                    favoriteMonthsForEmployees
            );
            if (!commonMonths.isEmpty()) {
                log.info("Employees with ids " + Arrays.toString(employees.stream()
                        .mapToInt(Employee::getUniqueId).toArray()) +
                        " have common vacation months: " +
                        commonMonths.toString());
            } else {
                log.info("Employees with ids " + Arrays.toString(employees.stream()
                        .mapToInt(Employee::getUniqueId).toArray()) +
                        " have not common vacation months ");
            }
        }
    }

    private void updateRegisteredFrom(List<Employee> employees, int limit) {
        if (employees.size() < limit) {
            registeredFrom = null;
        }

        registeredFrom = employees.stream()
                .map(Employee::getRegisteredAt)
                .max(LocalDateTime::compareTo)
                .get();
    }

    private Set<Integer> calculateCommonMonths(
            List<Integer> favoriteMonthsForEmployees
    ) {
        Set<Integer> commonMonths = new HashSet<>();
        return favoriteMonthsForEmployees.stream()
                .filter(month -> !commonMonths.add(month))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void saveFavoriteMonth(Employee employee, List<Integer> months) {
        EmployeesFavoriteMonth employeesFavoriteMonth = new EmployeesFavoriteMonth(
                months.toString(),
                LocalDateTime.now(),
                employee
        );
        employeeRepository.saveFavoriteMonth(employeesFavoriteMonth);
    }
}
