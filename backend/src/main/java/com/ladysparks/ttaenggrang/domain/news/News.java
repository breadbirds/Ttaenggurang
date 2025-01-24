package com.ladysparks.ttaenggrang.domain.news;

import com.ladysparks.ttaenggrang.domain.etf.Etf;
import com.ladysparks.ttaenggrang.domain.stock.Stock;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title; // 주식 이름

    @Column
    private String content;  // 한 주 가격

    @Column
    private float impact; //총 수량

    @Column
    private Timestamp created_at;  //생성일

    @Column
    private Timestamp updated_at;  // 수정일

    @Column
    private String category;  //카테고리


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
