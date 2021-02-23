package com.bamboo.employee.mapper;

import com.bamboo.employee.entity.FavoriteVacationEntity;
import com.bamboo.employee.model.FavoriteVacation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteVacationMapper {

    FavoriteVacationMapper INSTANCE =
            Mappers.getMapper(FavoriteVacationMapper.class);

    FavoriteVacationEntity favoriteVacationToEntity(FavoriteVacation favoriteVacation);
    FavoriteVacation entityToFavoriteVaction(FavoriteVacationEntity favoriteVacationEntity);
}
