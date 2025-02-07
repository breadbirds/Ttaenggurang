package com.ladysparks.ttaenggrang.domain.bank.repository;

import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingsDepositRepository extends JpaRepository<SavingsDeposit, Long> {

    List<SavingsDeposit> findBySavingsSubscriptionId(Long savingsSubscriptionId);

}