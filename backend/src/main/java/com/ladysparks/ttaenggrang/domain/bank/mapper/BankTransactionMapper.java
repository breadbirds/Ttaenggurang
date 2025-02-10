package com.ladysparks.ttaenggrang.domain.bank.mapper;

import com.ladysparks.ttaenggrang.global.config.MapStructConfig;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransaction;
import com.ladysparks.ttaenggrang.domain.bank.dto.BankTransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface BankTransactionMapper {

    BankTransactionMapper INSTANCE = Mappers.getMapper(BankTransactionMapper.class);

    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    @Mapping(source = "receiver.id", target = "receiverId")
    BankTransactionDTO toDto(BankTransaction bankTransaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bankAccountId", target = "bankAccount.id")
    @Mapping(source = "receiverId", target = "receiver.id")
    BankTransaction toEntity(BankTransactionDTO bankTransactionDTO);

}
