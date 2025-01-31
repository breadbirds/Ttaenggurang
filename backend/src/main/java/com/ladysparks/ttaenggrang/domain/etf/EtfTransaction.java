package com.ladysparks.ttaenggrang.domain.etf;

import com.ladysparks.ttaenggrang.domain.stock.TransType;
import com.ladysparks.ttaenggrang.domain.user.Student;
import com.ladysparks.ttaenggrang.domain.user.Teacher;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class EtfTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int share_count;  // ETF 거래 수량

    @Column
    private Timestamp trans_date;  // 거래 날짜

    @Column
    private int purchase_prc;   // 거래 당시 1주 가격

    @Column
    private int total_amt;  // 총 거래 금액

    @Column
    private int return_amt;   // 현재 주가

    // 거래 유형
    @Enumerated(EnumType.STRING)  // Enum 값을 문자열로 저장
    @Column(name = "trans_type")
    private TransType tranTransTypesType;

    //조인

    //ETF
    @ManyToOne
    @JoinColumn(name = "etf_id")
    private Etf etf;

    //학생
    @ManyToOne(targetEntity = Student.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = true)  // 학생 정보가 없을 수도 있음을 나타냄
    private Student student;



}
