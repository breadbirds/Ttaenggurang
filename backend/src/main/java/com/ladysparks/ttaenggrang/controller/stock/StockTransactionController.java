package com.ladysparks.ttaenggrang.controller.stock;

import com.ladysparks.ttaenggrang.domain.stock.StockTransaction;
import com.ladysparks.ttaenggrang.service.stock.StockTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class StockTransactionController {

    private final StockTransactionService transactionService;

    public StockTransactionController(StockTransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    @GetMapping("/students/{testStudentId}/transactions")
//    public ResponseEntity<List<StockTransaction>> getTestUserTransactions(@PathVariable("testStudentId") int testStudentId) {
//        List<StockTransaction> transactions = transactionService.getTransactionsForTestUser(testStudentId);
//        return ResponseEntity.ok(transactions);
//    }

    // 특정 학생의 거래 내역 조회
//    @GetMapping("/student/{studentId}")
//    public ResponseEntity<List<StockTransaction>> getTransactionsByStudent(@PathVariable("studentId") int studentId) {
//        List<StockTransaction> transactions = transactionService.getTransactionsByStudent(studentId);
//
//        if (transactions.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//        return ResponseEntity.ok(transactions);
//    }
}