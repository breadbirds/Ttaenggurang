package com.ladysparks.ttaenggrang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankTransactionDTO {

    private Long id;
    private Long bankAccountId;
    private Long referenceId;
    private int amount;
    private int balanceBefore;
    private int balanceAfter;
    private String description;
    private Timestamp createdAt;

}
