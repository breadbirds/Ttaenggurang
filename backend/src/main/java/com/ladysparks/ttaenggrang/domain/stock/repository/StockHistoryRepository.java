package com.ladysparks.ttaenggrang.domain.stock.repository;

import com.ladysparks.ttaenggrang.domain.stock.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {

//    @Query("SELECT SUM(sh.volume) FROM StockHistory sh WHERE sh.student.id = :studentId AND sh.transType = 'BUY'")
//    int getTotalBuyVolume(@Param("studentId") Long studentId);
//
//    int getTotalSellVolume(Long id);
}
