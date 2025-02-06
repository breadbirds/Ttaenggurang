package com.ladysparks.ttaenggrang.domain.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsSubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(value = {"id", "status", "depositAmount", "startDate", "endDate", "status", "createdAt", "depositSchedule"}, allowGetters = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavingsSubscriptionDTO {

    private Long id;
    private Long savingsProductId;
    private Long studentId;
    private int depositAmount;
    private int durationWeeks;
    private Date startDate;
    private Date endDate;
    private SavingsSubscriptionStatus status;
    private DayOfWeek depositDayOfWeek;
    private Timestamp createdAt;

    // 자동 납입 일정
    private List<LocalDate> depositSchedule;
}
