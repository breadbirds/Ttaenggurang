package com.ladysparks.ttaenggrang.repository.stock;

import com.ladysparks.ttaenggrang.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {

}