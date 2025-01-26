package com.ladysparks.ttaenggrang.dto;

import com.ladysparks.ttaenggrang.domain.bank.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccountDTO {

    private Long id;
    private String accountNumber;
    private int balance;
    private Timestamp createdAt;

}
