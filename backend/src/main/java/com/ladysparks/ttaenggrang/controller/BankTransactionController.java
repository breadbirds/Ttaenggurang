package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.BankTransactionApiSpecification;
import com.ladysparks.ttaenggrang.dto.BankTransactionDTO;
import com.ladysparks.ttaenggrang.response.ApiResponse;
import com.ladysparks.ttaenggrang.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank-transactions")
public class BankTransactionController implements BankTransactionApiSpecification {

    private final BankTransactionService bankTransactionService;

    @Autowired
    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    // 은행 계좌 거래 내역 [전체 조회]
    @GetMapping
    public ResponseEntity<ApiResponse<List<BankTransactionDTO>>> bankTransactionList(@RequestParam Long studentId) {
        List<BankTransactionDTO> bankTransactionDTOList = bankTransactionService.findBankTransactions(studentId);
        return ResponseEntity.ok(ApiResponse.success(bankTransactionDTOList));
    }

}
