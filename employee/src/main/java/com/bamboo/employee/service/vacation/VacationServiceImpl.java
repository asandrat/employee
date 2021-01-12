package com.bamboo.employee.service.vacation;


import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.repository.employee.EmployeeRepository;
import com.bamboo.employee.repository.vacation.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationServiceImpl implements VacationService {


    private VacationRepository vacationRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public VacationServiceImpl(final VacationRepository vacationRepository,
                               final EmployeeRepository employeeRepository) {
        this.vacationRepository = vacationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addVacation(final Vacation vacation) {
        Employee employee = employeeRepository.read(vacation.getId().getEmployeeId());
        if (employee != null) {
            vacationRepository.create(vacation);
//            employee.addVacation(vacation);
            employeeRepository.addVacationToEmployee(vacation);
        } else {
            System.out.println("No such employee to associate vacation with");
        }
    }

    @Override
    public Vacation getVacation(final VacationId id) {
        return vacationRepository.read(id);
    }

    @Override
    public void removeVacation(VacationId id) {
        vacationRepository.delete(id);
        employeeRepository.removeEmployeesVacation(id);
    }

    @Override
    public void approveVacation(final VacationId vacationId) {
        vacationRepository.update(vacationId, VacationStatus.APPROVED);
    }

    @Override
    public void rejectVacation(final VacationId vacationId) {
        vacationRepository.update(vacationId, VacationStatus.REJECTED);
    }
}
