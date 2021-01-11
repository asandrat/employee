package com.bamboo.employee.service;


import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.repository.EmployeeRepository;
import com.bamboo.employee.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationServiceImpl implements VacationService {


    private VacationRepository repository;

    @Autowired
    public VacationServiceImpl(VacationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addVacation(final Vacation vacation) {
        repository.create(vacation);
    }

    @Override
    public Vacation getVacation(final VacationId id) {
        return repository.read(id);
    }
}
