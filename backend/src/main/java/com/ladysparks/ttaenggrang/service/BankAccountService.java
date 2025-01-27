package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.bank.BankAccount;
import com.ladysparks.ttaenggrang.dto.BankAccountDTO;
import com.ladysparks.ttaenggrang.mapper.BankAccountMapper;
import com.ladysparks.ttaenggrang.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Transactional
    public BankAccountDTO addBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount entity = bankAccountMapper.toEntity(bankAccountDTO);
        BankAccount savedEntity = bankAccountRepository.save(entity);
        return bankAccountMapper.toDto(savedEntity);
    }

    public Optional<BankAccountDTO> findBankAccount(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId)
                .map(bankAccountMapper::toDto); // ✅ Optional 처리
        // 만약 값이 존재하면 toDto()가 실행되고, 없으면 Optional.empty()를 반환
    }

}
