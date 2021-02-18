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
    public void calculateFavoriteMonthsForEmployees() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Employee> employees = employeeRepository.findOldest3();
        List<Integer> favoriteMonthsForAll3Employees = new ArrayList<>();

        for (Employee employee : employees) {
            Future<List<Integer>> future = executorService.submit(
                    new CronJobProcessor(this, employee)
            );

            try {
                favoriteMonthsForAll3Employees.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (employees.size() >= 2) {
            Set<Integer> commonMonths = calculateCommonMonths(
                    favoriteMonthsForAll3Employees
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

    private Set<Integer> calculateCommonMonths(
            List<Integer> commonMonths
    ) {
        Set<Integer> items = new HashSet<>();
        return commonMonths.stream()
                .filter(n -> !items.add(n))
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
