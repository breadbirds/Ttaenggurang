package com.ladysparks.ttaenggrang.dto;

import com.ladysparks.ttaenggrang.domain.bank.SavingsGoalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavingsGoalDTO {

    private Long id;
    private Long studentId;
    private String name;
    private int targetAmount;
    private SavingsGoalStatus status;
    private Timestamp createdAt;

}
