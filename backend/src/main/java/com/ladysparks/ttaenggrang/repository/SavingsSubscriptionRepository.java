package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.bank.SavingsSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsSubscriptionRepository extends JpaRepository<SavingsSubscription, Long> {

    List<SavingsSubscription> findByStudentId(Long studentId);
    List<SavingsSubscription> findBySavingsProductId(Long savingsProductId);

    boolean existsByStudentIdAndSavingsProductId(Long studentId, Long savingsProductId);

}
