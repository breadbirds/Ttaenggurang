package com.ladysparks.ttaenggrang.domain.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransactionType;
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

    @JsonIgnore
    private Long id;

    private Long bankAccountId;

    @JsonIgnore
    private Long referenceId;

    private BankTransactionType type;

    private int amount;

    @JsonIgnore
    private int balanceBefore;

    @JsonIgnore
    private int balanceAfter;

    private String description;

    @JsonIgnore
    private Timestamp createdAt;

}
