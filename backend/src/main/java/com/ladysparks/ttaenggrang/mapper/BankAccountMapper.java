package com.ladysparks.ttaenggrang.mapper;

import com.ladysparks.ttaenggrang.config.MapStructConfig;
import com.ladysparks.ttaenggrang.domain.bank.BankAccount;
import com.ladysparks.ttaenggrang.dto.BankAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface BankAccountMapper {

    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    // Entity → DTO 변환
    BankAccountDTO toDto(BankAccount bankAccount);

    // DTO → Entity 변환
    @Mapping(target = "id", ignore = true) // ID는 자동 생성되므로 무시
    BankAccount toEntity(BankAccountDTO bankAccountDto);

}
