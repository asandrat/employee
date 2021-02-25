package com.bamboo.employee.service.employee;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.entitiesDB.EmployeesFavouriteMonth;
import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.mapstruct.EmployeeMapper;
import com.bamboo.employee.mapstruct.VacationMapper;
import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.repositoryDB.employee.EmployeeRepositoryDB;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepositoryDB employeeRepositoryDB;
    private final EmployeeMapper employeeMapper;
    private final VacationMapper vacationMapper;

    private LocalDate registered = null;

    public EmployeeServiceImpl(EmployeeRepositoryDB employeeRepositoryDB,
                               EmployeeMapper employeeMapper,
                               VacationMapper vacationMapper) {
        this.employeeRepositoryDB = employeeRepositoryDB;
        this.employeeMapper = employeeMapper;
        this.vacationMapper = vacationMapper;
    }

    @Override
    public EmployeeDTO addEmployee(String name, String surname,
                                   String registrationDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate registrationDate = LocalDate.parse(registrationDateString,
                formatter);
        Employee employeeDB = new Employee(name, surname, registrationDate);
        employeeRepositoryDB.addEmployee(employeeDB);
        return employeeMapper.employeeEntityToEmployeeDTO(employeeDB);
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
        List<Employee> listOfEmployees =
                new ArrayList<>(employeeRepositoryDB.findAllEmployees());
        return employeeMapper.map(listOfEmployees);
    }

    @Override
    public Collection<VacationDTO> findAllVacationsOfEmployee(String id) {
        long longId = Long.parseLong(id);
        List<Vacation> listOfVacations = new ArrayList<>(
                employeeRepositoryDB.findAllVacationsOfEmployee(longId));
        Collection<VacationDTO> vacationDTOList = new ArrayList<>();
        String employeeId;
        for (Vacation vacation : listOfVacations) {
            VacationDTO vacationDTO = vacationMapper
                    .vacationEntityToVacationDTO(vacation);
            employeeId = String.valueOf(vacation.getEmployee().getId());
            vacationDTO.setEmployeeId(employeeId);
            vacationDTOList.add(vacationDTO);
        }
        return vacationDTOList;
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
        return employeeMapper.map(employees);
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
