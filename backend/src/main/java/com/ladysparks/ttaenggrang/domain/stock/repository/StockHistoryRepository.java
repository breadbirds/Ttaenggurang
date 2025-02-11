package com.ladysparks.ttaenggrang.domain.stock.repository;

import com.ladysparks.ttaenggrang.domain.stock.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {

    // 매수량 합계 조회 (BUY 거래만 필터링)
    @Query("SELECT COALESCE(SUM(st.share_count), 0) FROM StockTransaction st WHERE st.student.id = :studentId AND st.transType = 'BUY'")
    int getTotalBuyVolume(@Param("studentId") Long studentId);

    // 매도량 합계 조회 (SELL 거래만 필터링)
    @Query("SELECT COALESCE(SUM(st.share_count), 0) FROM StockTransaction st WHERE st.student.id = :studentId AND st.transType = 'SELL'")
    int getTotalSellVolume(@Param("studentId") Long studentId);


    // 주식 ID로 StockHistory 리스트 찾기
    List<StockHistory> findByStockId(Long id);

    // 주식 ID로 모든 거래량 초기화
    @Modifying
    @Query("UPDATE StockHistory sh SET sh.buyVolume = 0, sh.sellVolume = 0 WHERE sh.stock.id = :stockId")
    void clearVolumeData(@Param("stockId") Long stockId);
}
