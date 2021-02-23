package com.bamboo.employee.mapper;

import com.bamboo.employee.entity.VacationEntity;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.dto.VacationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VacationMapper {

    VacationMapper INSTANCE = Mappers.getMapper(VacationMapper.class);

    VacationDTO vacationToDTO(Vacation vacation);

    Vacation vacationDTOtoVacation(VacationDTO vacationDTO);

    Vacation entityToVacation(VacationEntity vacationEntity);

    VacationEntity vacationToEntity(Vacation vacation);
}
