package com.ladysparks.ttaenggrang.dto.stock;

import com.ladysparks.ttaenggrang.domain.stock.Button;
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
    private String name;        // 주식 이름
    private int price_per;// 한 주당 가격
    private int total_qty;      // 총 수량
    private int remain_qty;     // 주식 재고 수량
    private String description; // 설명
    private Timestamp created_at; // 생성일
    private Timestamp update_at;  // 수정일
    private String category;      // 카테고리
    private Button button;           //버튼

    //조인
    private Long teacher_id;    // 교사 ID
    private Long etf_id;        // ETF ID



}
