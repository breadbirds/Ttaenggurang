package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.bank.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    List<BankTransaction> findByBankAccountId(Long bankAccountId);

}

