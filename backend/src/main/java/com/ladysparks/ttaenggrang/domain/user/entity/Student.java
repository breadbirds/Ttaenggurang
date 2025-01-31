package com.ladysparks.ttaenggrang.domain.user.entity;

import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.etf.entity.EtfTransaction;
import com.ladysparks.ttaenggrang.domain.stock.entity.StockTransaction;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자를 자동으로 생성
@NoArgsConstructor //기본 생성자(매개변수가 없는 생성자)를 자동으로 생성 , Entity 사용 하면 사용 해줘야함!
@Entity  // DB 매핑
@Builder  //Builder 패턴을 생성하여 객체를 생성
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 학생 ID

    @OneToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    @Column
    private byte[] password;

    @Column(length = 100)
    private String name;

    @Column(length = 2083)
    private byte[] profile_image;  // 이미지 파일 경로

    @Column
    private Timestamp created_at;


    // 조인

    //주식 거래내역
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<StockTransaction> transactions;

    //ETF 거래내역
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<EtfTransaction> etfTransactions;
}
