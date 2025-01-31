package com.ladysparks.ttaenggrang.repository.stock;

import com.ladysparks.ttaenggrang.domain.stock.Stock;
import com.ladysparks.ttaenggrang.domain.stock.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
    // 특정 학생의 주식 거래 내역 조회
    List<StockTransaction> findByStudentId(int studentId);

}
