package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.stock.Stock;
import com.ladysparks.ttaenggrang.domain.stock.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
}
