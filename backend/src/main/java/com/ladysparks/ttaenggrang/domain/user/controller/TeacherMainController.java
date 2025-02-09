package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.domain.bank.dto.StudentDailyAverageFinancialDTO;
import com.ladysparks.ttaenggrang.domain.bank.service.BankTransactionService;
import com.ladysparks.ttaenggrang.global.docs.TeacherMainApiSpecification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers/{teacherId}")
public class TeacherMainController implements TeacherMainApiSpecification {

    private final BankTransactionService bankTransactionService;

    public TeacherMainController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @GetMapping("/daily-average-income-expense")
    public ResponseEntity<List<StudentDailyAverageFinancialDTO>> getDailyAverageIncomeAndExpense() {
        List<StudentDailyAverageFinancialDTO> response = bankTransactionService.getDailyAverageIncomeAndExpense();
        return ResponseEntity.ok(response);
    }

}
