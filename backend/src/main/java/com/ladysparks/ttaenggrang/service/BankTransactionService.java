package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.bank.BankTransaction;
import com.ladysparks.ttaenggrang.dto.BankTransactionDTO;
import com.ladysparks.ttaenggrang.mapper.BankTransactionMapper;
import com.ladysparks.ttaenggrang.repository.BankTransactionRepository;
import com.ladysparks.ttaenggrang.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankTransactionService {

    private final StudentRepository studentRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final BankTransactionMapper bankTransactionMapper;

    @Autowired
    public BankTransactionService(StudentRepository studentRepository, BankTransactionRepository bankTransactionRepository, BankTransactionMapper bankTransactionMapper) {
        this.studentRepository = studentRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankTransactionMapper = bankTransactionMapper;
    }

    public List<BankTransactionDTO> findBankTransactions(Long studentId) {
        // 1. StudentId를 기반으로 bankAccountId 찾기
        Long bankAccountId = studentRepository.findBankAccountIdById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 학생의 계좌를 찾을 수 없습니다. ID: " + studentId));

        // 2. BankAccountId를 기반으로 거래 내역 조회 후 DTO 변환
        return bankTransactionRepository.findByBankAccountId(bankAccountId)
                .stream()
                .map(bankTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

}
