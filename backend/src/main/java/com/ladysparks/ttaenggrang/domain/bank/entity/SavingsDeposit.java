package com.ladysparks.ttaenggrang.domain.bank.entity;

import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SavingsDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "savings_subscription_id", nullable = false)
    private SavingsSubscription savingsSubscription;

    @Column(nullable = false)
    private int depositAmount;

    @Column(nullable = false)
    private Date depositDate;

    @Column(nullable = false)
    private Boolean isDeposited;

    @CreationTimestamp
    private Timestamp createdAt;

}
