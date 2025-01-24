package com.ladysparks.ttaenggrang.domain.etf;

import com.ladysparks.ttaenggrang.domain.stock.Stock;
import com.ladysparks.ttaenggrang.domain.stock.StockHistory;
import com.ladysparks.ttaenggrang.domain.user.Teacher;
import com.ladysparks.ttaenggrang.domain.news.News;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Etf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name; //ETF 이름

    @Column
    private int price_per; // 한 주당 가격

    @Column
    private int total_qty;      // 총 수량

    @Column
    private int remain_qty;     // ETF 재고 수량

    @Column
    private String description; // 설명

    @Column
    private Timestamp created_at; // 생성일

    @Column
    private Timestamp update_at;  // 수정일

    @Column(precision = 5, scale = 4)  //비중
    private BigDecimal weight;

    // 조인

    //선생님
    @ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id") // 외래 키(Foreign Key) 설정
    private Teacher teacher;

    //과거 추이 기록
    @OneToMany(mappedBy = "etf", fetch = FetchType.LAZY)
    private List<StockHistory> stockHistories; // 여러 과거 추이 기록

    //주식
    @OneToMany(targetEntity = Stock.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private List<Stock> stocks;

    //ETF 거래 내역
    @OneToMany(targetEntity = EtfTransaction.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "etfTransaction_id")
    private List<EtfTransaction> etfTransaction;

    //뉴스
    @ManyToMany(mappedBy = "etfs")
    private List<News> news;


}
