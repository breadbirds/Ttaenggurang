package com.ladysparks.ttaenggrang.mapper;

import com.ladysparks.ttaenggrang.config.MapStructConfig;
import com.ladysparks.ttaenggrang.domain.bank.BankTransaction;
import com.ladysparks.ttaenggrang.dto.BankTransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface BankTransactionMapper {

    BankTransactionMapper INSTANCE = Mappers.getMapper(BankTransactionMapper.class);

    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    BankTransactionDTO toDto(BankTransaction bankTransaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bankAccountId", target = "bankAccount.id")
    BankTransaction toEntity(BankTransactionDTO bankTransactionDTO);

}
