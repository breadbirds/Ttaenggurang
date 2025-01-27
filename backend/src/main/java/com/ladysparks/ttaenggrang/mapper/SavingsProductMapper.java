package com.ladysparks.ttaenggrang.mapper;

import com.ladysparks.ttaenggrang.config.MapStructConfig;
import com.ladysparks.ttaenggrang.domain.bank.SavingsProduct;
import com.ladysparks.ttaenggrang.dto.SavingsProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface SavingsProductMapper {

    SavingsProductMapper INSTANCE = Mappers.getMapper(SavingsProductMapper.class);

    @Mapping(source = "teacher.id", target = "teacherId")
    SavingsProductDTO toDto(SavingsProduct savingsProduct);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "teacherId", target = "teacher.id")
    SavingsProduct toEntity(SavingsProductDTO savingsProductDTO);

}
