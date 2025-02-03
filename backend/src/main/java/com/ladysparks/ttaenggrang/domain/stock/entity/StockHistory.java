package com.ladysparks.ttaenggrang.domain.stock.entity;

import com.ladysparks.ttaenggrang.domain.etf.entity.Etf;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int price;  //해당 날짜의 주식 가격

    @Column
    private int volume; //해당 날짜의 거래량

    @Column
    private Timestamp date;  //기록된 날짜


    //조인

    //주식
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;  // 하나의 주식

    //ETF
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etf_id")
    private Etf etf;  // ETF

}
