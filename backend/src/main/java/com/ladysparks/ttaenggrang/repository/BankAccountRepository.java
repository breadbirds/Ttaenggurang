package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.bank.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {}
