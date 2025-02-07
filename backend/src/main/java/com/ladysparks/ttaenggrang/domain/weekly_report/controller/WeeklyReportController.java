package com.ladysparks.ttaenggrang.domain.weekly_report.controller;

import com.ladysparks.ttaenggrang.domain.weekly_report.dto.WeeklyReportDTO;
import com.ladysparks.ttaenggrang.domain.weekly_report.service.WeeklyReportService;
import com.ladysparks.ttaenggrang.global.docs.WeeklyReportApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weekly")
public class WeeklyReportController implements WeeklyReportApiSpecification {

    private final WeeklyReportService reportService;

    public WeeklyReportController(WeeklyReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<WeeklyReportDTO>> WeeklyReportDetails(@RequestParam Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(reportService.createWeeklyReport(studentId)));
    }

}

