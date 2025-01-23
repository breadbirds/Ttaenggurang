package com.ladysparks.ttaenggrang.dto;

import lombok.*;
import org.w3c.dom.Text;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDTO {
    private Long id;            // 주식 ID
    private Long teacher_id;    // 교사 ID
    private String name;        // 주식 이름
    private int price_per_share;// 한 주당 가격
    private int total_qty;      // 총 수량
    private int remain_qty;     // 주식 재고 수량
    private String description; // 설명
    private Timestamp created_at; // 생성일
    private Timestamp update_at;  // 수정일
    private String category;      // 카테고리



}
