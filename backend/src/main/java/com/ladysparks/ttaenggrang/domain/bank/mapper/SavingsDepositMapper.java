package com.ladysparks.ttaenggrang.domain.bank.mapper;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsDepositDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SavingsDepositMapper {

    @Mapping(source = "savingsSubscription.id", target = "savingsSubscriptionId")
    SavingsDepositDTO toDto(SavingsDeposit entity);

    @Mapping(source = "savingsSubscriptionId", target = "savingsSubscription.id")
    SavingsDeposit toEntity(SavingsDepositDTO dto);

}