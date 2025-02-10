package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.domain.bank.dto.StudentDailyAverageFinancialDTO;
import com.ladysparks.ttaenggrang.domain.bank.service.BankTransactionService;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherDashboardDTO;
import com.ladysparks.ttaenggrang.domain.user.service.TeacherDashboardService;
import com.ladysparks.ttaenggrang.global.docs.TeacherDashboardApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers/{teacherId}/dashboard")
public class TeacherDashboardController implements TeacherDashboardApiSpecification {

    private final TeacherDashboardService teacherDashboardService;
    private final BankTransactionService bankTransactionService;

    @GetMapping("/daily-average-income-expense")
    public ResponseEntity<ApiResponse<List<StudentDailyAverageFinancialDTO>>> DailyAverageIncomeAndExpense() {
        List<StudentDailyAverageFinancialDTO> response = bankTransactionService.getDailyAverageIncomeAndExpense();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TeacherDashboardDTO>> TeacherDashboardDetails() {
        TeacherDashboardDTO teacherDashboardDTO = teacherDashboardService.getTeacherDashboardData();
        return ResponseEntity.ok(ApiResponse.success(teacherDashboardDTO));
    }

}
