package com.ladysparks.ttaenggrang.domain.stock;

import com.ladysparks.ttaenggrang.domain.etf.Etf;
import com.ladysparks.ttaenggrang.domain.news.News;
import com.ladysparks.ttaenggrang.domain.user.Teacher;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 주식 ID

    @Column
    private String name;        // 주식 이름

    @Column
    private int price_per;// 한 주당 가격

    @Column
    private int total_qty;      // 총 수량

    @Column
    private int remain_qty;     // 주식 재고 수량

    @Column
    private String description; // 설명

    @Column
    private Timestamp created_at; // 생성일

    @Column
    private Timestamp update_at;  // 수정일

    @Column
    private String category;      // 카테고리

    @Enumerated(EnumType.STRING)  // on/off 버튼
    @Column(name = "button")
    private Button button;

    //조인

    //교사
    @ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id") // 외래 키(Foreign Key) 설정
    private Teacher teacher;

    //과거 추이 기록
    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
    private List<StockHistory> stockHistories; // 여러 과거 추이 기록

    //뉴스
    @ManyToMany(mappedBy = "stocks")
    private List<News> news;

    //주식 거래 내역
    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
    private List<StockTransaction> stockTransaction;

    //ETF
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etf_id")
    private Etf etf;




}
