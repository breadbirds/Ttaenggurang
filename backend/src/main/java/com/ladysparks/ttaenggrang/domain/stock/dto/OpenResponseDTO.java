package com.ladysparks.ttaenggrang.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenResponseDTO {
    private int price_per;      // 한 주당 가격
    private int total_qty;      // 총 수량
    private int remain_qty;     // 주식 재고 수량
    private Integer changeRate;


}
