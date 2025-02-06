package com.ladysparks.ttaenggrang.domain.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@JsonIgnoreProperties(value={"id", "referenceId", "balanceBefore", "balanceAfter", "createdAt"}, allowGetters=true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankTransactionDTO {

    private Long id;

    private Long bankAccountId;

    private Long referenceId;

    private BankTransactionType type;

    private int amount;

    private int balanceBefore;

    private int balanceAfter;

    private String description;

    private Timestamp createdAt;

}
