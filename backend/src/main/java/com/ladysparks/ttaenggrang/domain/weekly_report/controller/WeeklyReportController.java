package com.ladysparks.ttaenggrang.domain.weekly_report.controller;

import com.ladysparks.ttaenggrang.domain.weekly_report.dto.StudentFinancialSummaryDTO;
import com.ladysparks.ttaenggrang.domain.weekly_report.dto.WeeklyFinancialSummaryDTO;
import com.ladysparks.ttaenggrang.domain.weekly_report.dto.WeeklyReportDTO;
import com.ladysparks.ttaenggrang.domain.weekly_report.service.WeeklyFinancialSummaryService;
import com.ladysparks.ttaenggrang.domain.weekly_report.service.WeeklyReportService;
import com.ladysparks.ttaenggrang.global.docs.WeeklyReportApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weekly-report")
public class WeeklyReportController implements WeeklyReportApiSpecification {

    private final WeeklyReportService weeklyReportService;
    private final WeeklyFinancialSummaryService weeklyFinancialSummaryService;

    @GetMapping
    public ResponseEntity<ApiResponse<WeeklyFinancialSummaryDTO>> WeeklyReportDetails() {
        return ResponseEntity.ok(ApiResponse.success(weeklyFinancialSummaryService.getThisWeekReport()));
    }

    @GetMapping("/growth")
    public ResponseEntity<ApiResponse<StudentFinancialSummaryDTO>> WeeklyReportGrowthList() {
        StudentFinancialSummaryDTO summaryDTO = weeklyFinancialSummaryService.getStudentWeeklySummary();
        return ResponseEntity.ok(ApiResponse.success(summaryDTO));
    }

}

