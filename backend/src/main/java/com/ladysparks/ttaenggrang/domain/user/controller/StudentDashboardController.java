package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.domain.user.dto.SavingsAchievementDTO;
import com.ladysparks.ttaenggrang.domain.user.service.StudentService;
import com.ladysparks.ttaenggrang.global.docs.StudentDashboardApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students/{student}")
@RequiredArgsConstructor
public class StudentDashboardController implements StudentDashboardApiSpecification {

    private final StudentService studentService;

    @GetMapping("/{studentId}/savings-achievement")
    public ResponseEntity<ApiResponse<SavingsAchievementDTO>> SavingsAchievementRate() {
        SavingsAchievementDTO savingsAchievementDTO = studentService.calculateSavingsAchievementRate();
        return ResponseEntity.ok(ApiResponse.success(savingsAchievementDTO));
    }

}
