package com.ladysparks.ttaenggrang.domain.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@JsonIgnoreProperties(value = {"id", "createdAt"}, allowGetters = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsDepositDTO {

    private Long id;
    private Long savingsSubscriptionId;
    private int depositAmount;
    private Date depositDate;
    private Boolean isDeposited;
    private Timestamp createdAt;

}
