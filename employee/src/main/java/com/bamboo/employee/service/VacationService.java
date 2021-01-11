package com.bamboo.employee.service;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationId;

public interface VacationService {

    void addVacation(Vacation vacation);

    Vacation getVacation(VacationId id);


}
