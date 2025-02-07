package com.ladysparks.ttaenggrang.domain.weekly_report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeeklyReportDTO {

    private int totalIncome;         // 총 수입
    private int totalExpense;        // 총 지출
    private int totalSavings;        // 총 저축
    private int totalInvestment;     // 총 투자 비용
    private int totalInvestmentReturn; // 총 투자 수익
    private int netBalanceChange;    // 순 자산 변화

}
