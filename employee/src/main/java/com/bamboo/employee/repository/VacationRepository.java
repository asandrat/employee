package com.bamboo.employee.repository;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;
import com.bamboo.employee.model.VacationStatus;

public interface VacationRepository {

    void create(Vacation vacation);

    Vacation read(VacationId vacationId);

    void delete(VacationId id);

    void update(VacationId vacationId, VacationStatus approved);
}
