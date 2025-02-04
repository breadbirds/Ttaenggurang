package com.ladysparks.ttaenggrang.domain.stock.dto;

import com.ladysparks.ttaenggrang.domain.stock.entity.Button;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import lombok.*;


import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDTO {
    private int id;            // 주식 ID
    private String name;        // 주식 이름
    private int price_per;// 한 주당 가격
    private int total_qty;      // 총 수량
    private int remain_qty;     // 주식 재고 수량
    private String description; // 설명
    private Timestamp created_at; // 생성일
    private Timestamp updated_at;  // 수정일
    private String category;      // 카테고리
    private Button button;           //버튼

    //조인
    private Long teacher_id;    // 교사 ID
    private Long etf_id;        // ETF ID



    // lombok을 사용한 Entity 객체 생성(builder) 방법
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
                .button(stockDto.getButton())

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
                .button(stock.getButton())
                .build();
    }

}
