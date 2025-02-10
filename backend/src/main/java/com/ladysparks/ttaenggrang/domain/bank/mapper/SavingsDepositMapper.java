package com.ladysparks.ttaenggrang.domain.bank.mapper;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsDepositDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsDeposit;
import com.ladysparks.ttaenggrang.global.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface SavingsDepositMapper {

    @Mapping(source = "savingsSubscription.id", target = "savingsSubscriptionId")
    SavingsDepositDTO toDto(SavingsDeposit entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "savingsSubscriptionId", target = "savingsSubscription.id")
    SavingsDeposit toEntity(SavingsDepositDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(source = "savingsSubscriptionId", target = "savingsSubscription.id")
    SavingsDeposit toUpdatedEntity(SavingsDepositDTO dto);

}