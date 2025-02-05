package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.BankAccountDTO;
import com.ladysparks.ttaenggrang.domain.bank.dto.BankTransactionDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransaction;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransactionType;
import com.ladysparks.ttaenggrang.domain.bank.mapper.BankTransactionMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankAccountRepository;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankTransactionRepository;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankTransactionService {

    private final StudentRepository studentRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final BankTransactionMapper bankTransactionMapper;

    @Autowired
    public BankTransactionService(StudentRepository studentRepository, BankAccountRepository bankAccountRepository, BankTransactionRepository bankTransactionRepository, BankTransactionMapper bankTransactionMapper) {
        this.studentRepository = studentRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankTransactionMapper = bankTransactionMapper;
    }

    @Transactional
    public BankTransactionDTO addBankTransaction(BankTransactionDTO bankTransactionDTO) {
        // 1. 거래할 계좌 조회
        BankAccount bankAccount = bankAccountRepository.findById(bankTransactionDTO.getBankAccountId())
                .orElseThrow(() -> new EntityNotFoundException("계좌를 찾을 수 없습니다. ID: " + bankTransactionDTO.getBankAccountId()));

        // 2. 거래 전 잔액 확인
        int balanceBefore = bankAccount.getBalance();
        int transactionAmount = bankTransactionDTO.getAmount();

        // 3. 입금 또는 출금 처리
        switch (bankTransactionDTO.getType()) {
            case DEPOSIT, SALE, STOCK_SELL:
                bankAccount.updateBalance(balanceBefore + transactionAmount); // 입금
                break;

            case WITHDRAWAL, PURCHASE, STOCK_BUY:
                if (balanceBefore < transactionAmount) {
                    throw new IllegalArgumentException("잔액이 부족합니다.");
                }
                bankAccount.updateBalance(balanceBefore - transactionAmount); // 출금
                break;

            case TRANSFER:
                System.out.println("송금 거래 처리");
                break;

            default:
                throw new IllegalArgumentException("유효하지 않은 거래 유형입니다.");
        }

        // 4. 거래 내역 저장
        BankTransaction bankTransaction = BankTransaction.createTransaction(
                bankAccount,
                bankTransactionDTO.getType(),
                transactionAmount,
                balanceBefore,
                bankAccount.getBalance(),
                bankTransactionDTO.getDescription()
        );

        // 5. 변경된 계좌 정보 저장
        bankAccountRepository.save(bankAccount);

        bankTransactionRepository.save(bankTransaction);

        // 6. DTO로 반환
        return bankTransactionMapper.toDto(bankTransaction);
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
