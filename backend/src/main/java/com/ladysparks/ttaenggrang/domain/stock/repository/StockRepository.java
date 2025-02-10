package com.ladysparks.ttaenggrang.domain.stock.repository;

import com.ladysparks.ttaenggrang.domain.stock.category.Category;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

//    List<StockDTO> findByCategory(String category);
    List<Stock> findByCategory(Category category);

    boolean existsByName(String name);

    // 현재 주식 평가액 조회
    @Query("SELECT COALESCE(SUM(st.owned_qty * s.price_per), 0) FROM StockTransaction st " +
            "JOIN Stock s ON st.stock.id = s.id " +
            "WHERE st.student.id = :studentId")
    int getCurrentStockEvaluation(@Param("studentId") Long studentId);

    // 지난주 주식 평가액 조회
    @Query("SELECT COALESCE(SUM(st.owned_qty * s.price_per), 0) FROM StockTransaction st " +
            "JOIN Stock s ON st.stock.id = s.id " +
            "WHERE st.student.id = :studentId " +
            "AND st.trans_date BETWEEN :startDate AND :endDate")
    int getLastWeekStockEvaluation(@Param("studentId") Long studentId,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(s.price_per * st.owned_qty), 0) FROM Stock s " +
            "JOIN StockTransaction st ON s.id = st.stock.id " +
            "WHERE st.student.id = :studentId AND st.trans_date BETWEEN :startDate AND :endDate")
    Optional<Integer> getStockEvaluationByDate(@Param("studentId") Long studentId,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

}