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
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;  // 학생 계정 (베이스ID + 숫자)

    @Column(nullable = false)
    private String password;  // 초기 비밀번호 (베이스ID와 동일)

    @Column(length = 50)
    private String name;  // 학생 이름

    @Column(length = 2083)
    private byte[] profile_image;  // 이미지 파일 경로

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    // <조인>
    // 교사
    @ManyToOne
    @JoinColumn(name = "teacherId", nullable = false)
    private Teacher teacher;

    @OneToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    //주식 거래내역
//    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
//    private List<StockTransaction> transactions;

    //ETF 거래내역
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    private List<EtfTransaction> etfTransactions;
}
