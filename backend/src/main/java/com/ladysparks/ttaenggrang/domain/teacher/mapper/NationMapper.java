package com.ladysparks.ttaenggrang.domain.teacher.mapper;

import com.ladysparks.ttaenggrang.domain.teacher.dto.NationDTO;
import com.ladysparks.ttaenggrang.domain.teacher.entity.Nation;
import com.ladysparks.ttaenggrang.global.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface NationMapper {

    NationMapper INSTANCE = Mappers.getMapper(NationMapper.class);

    NationDTO toDto(Nation nation);

    @Mapping(target = "id", ignore = true)
    Nation toEntity(NationDTO nationDTO);

}
