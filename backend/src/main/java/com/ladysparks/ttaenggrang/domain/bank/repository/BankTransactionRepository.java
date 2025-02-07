package com.ladysparks.ttaenggrang.domain.bank.repository;

import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    List<BankTransaction> findByBankAccountId(Long bankAccountId);

    @Query("SELECT t FROM BankTransaction t WHERE t.bankAccount.id = :bankAccountId AND t.createdAt BETWEEN :startDate AND :endDate")
    List<BankTransaction> findTransactionsByBankAccountAndDateRange(
            @Param("bankAccountId") Long bankAccountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}

