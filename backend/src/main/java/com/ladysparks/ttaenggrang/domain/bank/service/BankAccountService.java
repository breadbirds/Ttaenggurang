package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.bank.dto.BankAccountDTO;
import com.ladysparks.ttaenggrang.domain.bank.mapper.BankAccountMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    // 은행 계좌 [등록]
    @Transactional
    public BankAccountDTO addBankAccount(BankAccountDTO bankAccountDTO) {
        // 1. 입력값 검증
        if (bankAccountDTO == null) {
            throw new IllegalArgumentException("요청된 계좌 정보가 유효하지 않습니다.");
        }

        // 2. 중복 계좌 번호 방지
        boolean exists = bankAccountRepository.existsByAccountNumber(bankAccountDTO.getAccountNumber());
        if (exists) {
            throw new IllegalArgumentException("이미 존재하는 계좌 번호입니다: " + bankAccountDTO.getAccountNumber());
        }

        // 3. Entity 변환 및 저장
        BankAccount entity = bankAccountMapper.toEntity(bankAccountDTO);
        BankAccount savedEntity = bankAccountRepository.save(entity);

        // 4. DTO 변환 후 반환
        return bankAccountMapper.toDto(savedEntity);
    }

    // 은행 계좌 [조회]
    public BankAccountDTO findBankAccount(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("해당 계좌를 찾을 수 없습니다. ID: " + bankAccountId));
        return bankAccountMapper.toDto(bankAccount);
    }

}
