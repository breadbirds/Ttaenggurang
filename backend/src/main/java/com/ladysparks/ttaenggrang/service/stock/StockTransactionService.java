package com.ladysparks.ttaenggrang.service.stock;

import com.ladysparks.ttaenggrang.domain.stock.StockTransaction;
import com.ladysparks.ttaenggrang.dto.stock.StockTransactionDTO;
import com.ladysparks.ttaenggrang.repository.stock.StockTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockTransactionService {

    private final StockTransactionRepository stockTransactionRepository;

    public StockTransactionService(StockTransactionRepository transactionRepository) {
        this.stockTransactionRepository = transactionRepository;
    }

    public List<StockTransactionDTO> findTransactionsByStudentId(int studentId) {
        List<StockTransaction> transactions = stockTransactionRepository.findByStudentId(studentId);
        return transactions.stream()
                .map(StockTransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }
//    public List<StockTransaction> getTransactionsForTestUser(int testStudentId) {
//        int testStudentId = 1; // 테스트용 학생 ID
//        return transactionRepository.findByStudentId(testStudentId);
//    }

//    // 특정 학생의 거래 내역 조회
//    public List<StockTransaction> getTransactionsByStudent(int studentId) {
//        return transactionRepository.findByStudentId(studentId);
//    }
}