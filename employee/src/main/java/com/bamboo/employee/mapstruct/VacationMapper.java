package com.bamboo.employee.mapstruct;

import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.model.VacationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacationMapper {

    @Mapping(source = "uniqueId", target = "id")
    VacationDTO vacationToVacationDTO(Vacation vacation);
    List<VacationDTO> map (List<Vacation> vacations);
}
