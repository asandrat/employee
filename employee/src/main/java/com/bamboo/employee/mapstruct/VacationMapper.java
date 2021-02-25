package com.bamboo.employee.mapstruct;

import com.bamboo.employee.entitiesDB.Vacation;
import com.bamboo.employee.model.VacationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VacationMapper {
    VacationDTO vacationEntityToVacationDTO(Vacation vacation);
}
