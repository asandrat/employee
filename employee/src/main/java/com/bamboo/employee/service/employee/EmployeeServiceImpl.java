package com.bamboo.employee.service.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.EmployeesFavouriteMonth;
import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repositoryDB.employee.EmployeeRepositoryDB;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepositoryDB employeeRepositoryDB;
    private final ModelMapper modelMapper;

    private LocalDate registered = null;

    public EmployeeServiceImpl(EmployeeRepositoryDB employeeRepositoryDB,
                               ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.employeeRepositoryDB = employeeRepositoryDB;
    }

    @Override
    public EmployeeDTO addEmployee(String name, String surname,
                                   String registrationDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate registrationDate = LocalDate.parse(registrationDateString,
                formatter);
        Employee employeeDB = new Employee(name, surname, registrationDate);
        employeeRepositoryDB.addEmployee(employeeDB);
        return modelMapper.map(employeeDB, EmployeeDTO.class);
    }

    @Override
    public void removeEmployee(String id) {
        long longId = Long.parseLong(id);
        Employee employee = employeeRepositoryDB.findEmployeeById(longId);
        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee with id " + id + " not found.");
        }
        employeeRepositoryDB.deleteEmployee(employee);
    }

    @Override
    public Collection<EmployeeDTO> findAll() {
        List<Employee> list =
                new ArrayList<>(employeeRepositoryDB.findAllEmployees());
        Type listType = new TypeToken<List<EmployeeDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public Collection<VacationDTO> findAllVacationsOfEmployee(String id) {
        long longId = Long.parseLong(id);
        List<Vacation> list = new ArrayList<>(
                employeeRepositoryDB.findAllVacationsOfEmployee(longId));
        Type listType = new TypeToken<List<VacationDTO>>() {
        }.getType();
        return modelMapper.map(list, listType);
    }

    @Override
    public Collection<EmployeeDTO> findFirstNRegisteredEmployees(int n) {
        List<Employee> employees =
                employeeRepositoryDB.findFirstNRegisteredEmployees(n,
                        registered);
        if (employees == null) {
            return null;
        }

        if (employees.size() < n) {
            registered = null;
        }

        registered = employees.stream()
                .map(Employee::getRegistrationDate)
                .max(LocalDate::compareTo)
                .orElse(null);

        if (registered == null) {
            return null;
        }
        Type listType = new TypeToken<List<EmployeeDTO>>() {
        }.getType();
        return modelMapper.map(employees, listType);
    }

    @Override
    public void saveFavouriteMonth(EmployeeDTO employeeDTO,
                                   int favouriteMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate registrationDate =
                LocalDate.parse(employeeDTO.getRegistrationDate(), formatter);

        Employee employeeDB = new Employee(employeeDTO.getName(),
                employeeDTO.getSurname(), registrationDate);
        employeeDB.setId(Long.parseLong(employeeDTO.getId()));
        EmployeesFavouriteMonth employeesFavouriteMonth =
                new EmployeesFavouriteMonth(employeeDB, favouriteMonth);

        employeeRepositoryDB.saveFavoriteMonth(employeesFavouriteMonth);
    }

}
