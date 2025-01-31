package com.ladysparks.ttaenggrang.domain.news.entity;

import com.ladysparks.ttaenggrang.domain.etf.entity.Etf;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title; // 뉴스 제목

    @Column
    private String content;  // 뉴스 내용

    @Column
    private float impact; // 영향 비율

    @Column
    private Timestamp created_by;  //생성자

    @Column
    private Timestamp created_at;  //생성일


    @Enumerated(EnumType.STRING)  // Enum 값을 문자열로 저장
    @Column(name = "News_Type")
    private NewsType newsType;


    //조인

    //주식
    @ManyToMany
    @JoinTable(
            name = "news_stock",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id"))
    private List<Stock> stocks;

    //ETF
    @ManyToMany
    @JoinTable(
            name = "news_etf",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "etf_id"))
    private List<Etf> etfs;





}
