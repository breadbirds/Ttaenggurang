package com.ladysparks.ttaenggrang.dto.stock;

import com.ladysparks.ttaenggrang.domain.stock.TransType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class StockTransactionDTO {
    private int id;
    private int share_count;  // 주식 거래 수량
    private Timestamp trans_date;  // 거래 날짜
    private int purchase_prc;   // 거래 당시 1주 가격
    private int total_amt;  // 총 거래 금액
    private int return_amt;   // 현재 주가
    private BigDecimal returnRate;  // 손익/손실 금액
    private TransType transType; // 거래 유형

    //조인

    // 학생 관련 (student_id 외래 키를 참조)
    private Long studentId;  // student_id 외래 키 (Student 엔티티 참조)

    // 계좌 관련 (bank_account_id 외래 키를 참조)
    private Long bankAccountId; // bank_account_id 외래 키 (BankAccount 엔티티 참조)

    // 주식 관련 (stock_id 외래 키를 참조)
    private Long stockId;     // stock_id 외래 키 (Stock 엔티티 참조)



}
