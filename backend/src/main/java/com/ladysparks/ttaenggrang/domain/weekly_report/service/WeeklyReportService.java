package com.ladysparks.ttaenggrang.domain.weekly_report.service;

import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsSubscription;
import com.ladysparks.ttaenggrang.domain.bank.service.BankTransactionService;
import com.ladysparks.ttaenggrang.domain.bank.service.SavingsSubscriptionService;
import com.ladysparks.ttaenggrang.domain.item.service.ItemTransactionService;
import com.ladysparks.ttaenggrang.domain.weekly_report.dto.WeeklyReportDTO;
import org.springframework.stereotype.Service;

@Service
public class WeeklyReportService {

    private final BankTransactionService bankService;
    private final ItemTransactionService itemService;
    private final SavingsSubscriptionService savingsService;

    public WeeklyReportService(BankTransactionService bankService, ItemTransactionService itemService, SavingsSubscriptionService savingsService) {
        this.bankService = bankService;
        this.itemService = itemService;
        this.savingsService = savingsService;
    }

    public WeeklyReportDTO createWeeklyReport(Long studentId) {
        return bankService.createWeeklyReport(studentId);
    }

}
