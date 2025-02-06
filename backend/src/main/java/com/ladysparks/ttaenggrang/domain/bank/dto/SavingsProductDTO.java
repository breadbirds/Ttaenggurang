package com.ladysparks.ttaenggrang.domain.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@JsonIgnoreProperties(value = {"id", "createdAt"})
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
