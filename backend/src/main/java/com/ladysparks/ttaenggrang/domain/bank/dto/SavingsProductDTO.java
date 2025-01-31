package com.ladysparks.ttaenggrang.domain.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavingsProductDTO {

    private Long id;
    private Long teacherId;
    private String name;
    private float interestRate;
    private float earlyInterestRate;
    private int durationWeeks;
    private int depositAmount;
    private Timestamp createdAt;

}
