package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.BankTransactionDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransaction;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransactionType;
import com.ladysparks.ttaenggrang.domain.bank.mapper.BankTransactionMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankAccountRepository;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankTransactionRepository;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import com.ladysparks.ttaenggrang.domain.weekly_report.dto.WeeklyReportDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
            case DEPOSIT, SALE, STOCK_SELL, ETF_SELL:
                bankAccount.updateBalance(balanceBefore + transactionAmount); // 입금
                break;

            case WITHDRAWAL, PURCHASE, STOCK_BUY, ETF_BUY:
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

    public WeeklyReportDTO createWeeklyReport(Long studentId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);

        // 1. 학생 정보 조회
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

        // 2. 학생의 은행 계좌 조회
        BankAccount bankAccount = bankAccountRepository.findById(student.getBankAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 학생의 은행 계좌를 찾을 수 없습니다. ID: " + studentId));

        Long bankAccountId = bankAccount.getId();

        // 3. 해당 학생의 은행 계좌에서 최근 7일간 거래 내역 조회
        List<BankTransaction> transactions = bankTransactionRepository.findTransactionsByBankAccountAndDateRange(bankAccountId, startDate, endDate);

        int totalIncome = transactions.stream()
                .filter(t -> t.getType() == BankTransactionType.DEPOSIT
                        || t.getType() == BankTransactionType.SALARY
                        || t.getType() == BankTransactionType.SALE
                        || t.getType() == BankTransactionType.BANK_INTEREST
                        || t.getType() == BankTransactionType.ETF_SELL
                        || t.getType() == BankTransactionType.STOCK_SELL)
                .mapToInt(BankTransaction::getAmount)
                .sum();

        int totalExpense = transactions.stream()
                .filter(t -> t.getType() == BankTransactionType.WITHDRAWAL
                        || t.getType() == BankTransactionType.PURCHASE
                        || t.getType() == BankTransactionType.ETF_BUY
                        || t.getType() == BankTransactionType.STOCK_BUY)
                .mapToInt(BankTransaction::getAmount)
                .sum();

        int totalSavings = transactions.stream()
                .filter(t -> t.getType() == BankTransactionType.SAVINGS_PAYMENT || t.getType() == BankTransactionType.BANK_INTEREST)
                .mapToInt(BankTransaction::getAmount)
                .sum();

        int totalInvestment = transactions.stream()
                .filter(t -> t.getType() == BankTransactionType.STOCK_BUY || t.getType() == BankTransactionType.ETF_BUY)
                .mapToInt(BankTransaction::getAmount)
                .sum();

        int totalInvestmentReturn = transactions.stream()
                .filter(t -> t.getType() == BankTransactionType.STOCK_SELL || t.getType() == BankTransactionType.ETF_SELL)
                .mapToInt(BankTransaction::getAmount)
                .sum();

        // 한 주의 첫 거래 이전 잔액과 마지막 거래 이후 잔액을 비교
        int netBalanceChange = 0;
        if (!transactions.isEmpty()) {
            int firstBalance = transactions.get(0).getBalanceBefore();
            int lastBalance = transactions.get(transactions.size() - 1).getBalanceAfter();
            netBalanceChange = lastBalance - firstBalance;
        }

        return new WeeklyReportDTO(
                totalIncome,
                totalExpense,
                totalSavings,
                totalInvestment,
                totalInvestmentReturn,
                netBalanceChange
        );
    }

}
