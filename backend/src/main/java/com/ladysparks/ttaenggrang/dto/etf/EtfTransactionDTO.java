package com.ladysparks.ttaenggrang.dto.etf;

import com.ladysparks.ttaenggrang.domain.stock.TransType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class EtfTransactionDTO {
    private Long id;
    private int share_count;    // ETF 거래 수량
    private Timestamp trans_date; // 거래 날짜
    private int purchase_prc; // 거래 당시 1주 가격
    private int total_amt;  // 총 거래 금액
    private int return_amt; // 현재 주가
    private TransType TransType; // 거래 유형

    //조인
    // ETF 관련 (ETF ID를 참조)
    private Long etfId;          // ETF ID

    // 학생 관련 (학생 ID를 참조)
    private Long studentId;      // 학생 ID

}
