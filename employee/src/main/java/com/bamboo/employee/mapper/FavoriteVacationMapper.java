package com.bamboo.employee.mapper;

import com.bamboo.employee.entity.FavoriteVacationEntity;
import com.bamboo.employee.model.FavoriteVacation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FavoriteVacationMapper {

    FavoriteVacationEntity favoriteVacationToEntity(FavoriteVacation favoriteVacation);

    FavoriteVacation entityToFavoriteVaction(FavoriteVacationEntity favoriteVacationEntity);
}
