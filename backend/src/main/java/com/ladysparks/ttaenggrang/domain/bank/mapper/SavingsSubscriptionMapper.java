package com.ladysparks.ttaenggrang.domain.bank.mapper;

import com.ladysparks.ttaenggrang.global.config.MapStructConfig;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsSubscription;
import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsSubscriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface SavingsSubscriptionMapper {

    SavingsSubscriptionMapper INSTANCE = Mappers.getMapper(SavingsSubscriptionMapper.class);

    @Mapping(source = "savingsProduct.id", target = "savingsProductId")
    @Mapping(source = "student.id", target = "studentId")
    SavingsSubscriptionDTO toDto(SavingsSubscription savingsSubscription);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "savingsProductId", target = "savingsProduct.id")
    @Mapping(source = "studentId", target = "student.id")
    SavingsSubscription toEntity(SavingsSubscriptionDTO savingsSubscriptionDTO);

}
