package com.bamboo.employee.repository;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VacationRepositoryImpl implements VacationRepository {

    private final Map<VacationId, Vacation> vacations = new HashMap<>();

    @Override
    public void create(final Vacation vacation) {
        VacationId key = vacation.getId();
        if (vacations.containsKey(key)) {
            System.out.println("Vacation with key: " + key + " already exists");
        } else {
            vacations.put(vacation.getId(), vacation);
            System.out.println("Successfully added vacation with key:" + vacation.getId());
        }
    }

    @Override
    public Vacation read(final VacationId vacationId) {
        return vacations.get(vacationId);
    }

    @Override
    public void delete(final VacationId id) {
        Vacation vacation = vacations.remove(id);
        if (vacation == null) {
            System.out.println("No such vacation with id:" + id + " to be removed");
        } else {
            System.out.println("Successfully removed vacation with id:" + id);
        }
    }
}
