package com.ladysparks.ttaenggrang.domain.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ladysparks.ttaenggrang.domain.stock.entity.Button;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import jakarta.persistence.Column;
import lombok.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDTO {
    private int id;            // 주식 ID
    private String name;        // 주식 이름
    private int price_per;      // 한 주당 가격
    private int total_qty;      // 총 수량
    private int remain_qty;     // 주식 재고 수량
    private String description; // 설명
    private Timestamp created_at; // 생성일
    private Timestamp updated_at;  // 수정일
    private String category;      // 카테고리
    private Integer changeRate;   // 주식 변동률
    private Boolean isMarketActive;  // 시장 활성화 여부 (Boolean으로 변경)
    @JsonIgnore
    private LocalDateTime priceChangeTime;  // 가격 변동 시간


    private Long teacher_id;    // 교사 ID
    @JsonIgnore
    private int etf_id;         // ETF ID

    public static Stock toEntity(StockDTO stockDto) {
        return Stock.builder()
                .id(stockDto.getId())
                .name(stockDto.getName())
                .price_per(stockDto.getPrice_per())
                .total_qty(stockDto.getTotal_qty())
                .remain_qty(stockDto.getRemain_qty())
                .description(stockDto.getDescription())
                .created_at(stockDto.getCreated_at())
                .updated_at(stockDto.getUpdated_at())
                .category(stockDto.getCategory())
                .changeRate(stockDto.getChangeRate())
                .isMarketActive(stockDto.getIsMarketActive())  // 주식장 활성화 여부
                .priceChangeTime(stockDto.getPriceChangeTime())  // 가격 변동 시
                .build();
    }

    public static StockDTO fromEntity(Stock stock) {
        return StockDTO.builder()
                .id(stock.getId())
                .name(stock.getName())
                .price_per(stock.getPrice_per())
                .total_qty(stock.getTotal_qty())
                .remain_qty(stock.getRemain_qty())
                .description(stock.getDescription())
                .created_at(stock.getCreated_at())
                .updated_at(stock.getUpdated_at())
                .category(stock.getCategory())
                .changeRate(stock.getChangeRate())
                .isMarketActive(stock.getIsMarketActive())  // 주식장 활성화 여부
                .priceChangeTime(stock.getPriceChangeTime())  // 가격 변동 시
                .build();
    }
}



