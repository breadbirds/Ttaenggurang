package com.ladysparks.ttaenggrang.domain.user;

import com.ladysparks.ttaenggrang.domain.bank.BankAccount;
import com.ladysparks.ttaenggrang.domain.etf.EtfTransaction;
import com.ladysparks.ttaenggrang.domain.stock.StockTransaction;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 학생 ID

    @OneToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bank_account_id;

    @Column
    private byte[] password;

    @Column(length = 100)
    private String name;

    @Column(length = 2083)
    private byte[] profile_image;  // 이미지 파일 경로

    @Column
    private Timestamp created_at;


    // 조인

    //주식
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<StockTransaction> transactions;

    //ETF
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<EtfTransaction> etfTransactions;
}
