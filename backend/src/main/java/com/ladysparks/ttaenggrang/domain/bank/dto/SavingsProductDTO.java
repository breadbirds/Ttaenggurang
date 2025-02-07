package com.ladysparks.ttaenggrang.domain.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@JsonIgnoreProperties(value = {"id", "createdAt"}, allowGetters = true)
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
    private Date saleStartDate;  // 판매 시작일
    private Date saleEndDate;    // 판매 종료일
    private Timestamp createdAt;

}
