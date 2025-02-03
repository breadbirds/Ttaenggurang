package com.ladysparks.ttaenggrang.domain.bank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    @Column
    private Long referenceId; // ✅ 동일한 referenceId로 송금 트랜잭션 연결

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankTransactionType type;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int balanceBefore;

    @Column(nullable = false)
    private int balanceAfter;

    @Column
    private String description;

    @CreationTimestamp
    private Timestamp createdAt;

}
