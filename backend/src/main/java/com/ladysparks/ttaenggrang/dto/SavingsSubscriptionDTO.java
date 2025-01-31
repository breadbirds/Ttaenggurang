package com.ladysparks.ttaenggrang.dto;

import com.ladysparks.ttaenggrang.domain.bank.SavingsSubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavingsSubscriptionDTO {

    private Long id;
    private Long savingsProductId;
    private Long studentId;
    private int depositAmount;
    private Date startDate;
    private Date endDate;
    private SavingsSubscriptionStatus status;
    private DayOfWeek depositDayOfWeek;
    private Timestamp createdAt;

}
