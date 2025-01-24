package com.ladysparks.ttaenggrang.dto.stock;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class StockHistoryDTO {
    private Long id;
    private int price;
    private int volume;
    private Timestamp date;

    //조인
    // Stock 관련 (stock_id 외래 키를 참조)
    private Long stockId;    // stock_id 외래 키 (Stock 엔티티 참조)

    // Etf 관련 (etf_id 외래 키를 참조)
    private Long etfId;      // etf_id 외래 키 (Etf 엔티티 참조

}
