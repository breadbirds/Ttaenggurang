package com.ladysparks.ttaenggrang.domain.vote.controller;

import com.ladysparks.ttaenggrang.domain.vote.service.SalaryService;
import com.ladysparks.ttaenggrang.global.docs.SalaryApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salaries")
@RequiredArgsConstructor
public class SalaryController implements SalaryApiSpecification {

    private final SalaryService salaryService;

    // ✅ 주급 업데이트 (투표 결과 기반)
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<String>> distributeSalaries() {
        ApiResponse<String> response = salaryService.distributeSalaries();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
