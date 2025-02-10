package com.ladysparks.ttaenggrang.domain.bank.entity;

public enum SavingsDepositStatus {
    PENDING,     // 예정됨 (예정일 전)
    COMPLETED,   // 납입 완료
    FAILED       // 미납 (잔액 부족)
}