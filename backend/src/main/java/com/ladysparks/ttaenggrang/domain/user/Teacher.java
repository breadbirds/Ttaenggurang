package com.ladysparks.ttaenggrang.domain.user;

import com.ladysparks.ttaenggrang.domain.etf.Etf;
import com.ladysparks.ttaenggrang.domain.stock.Stock;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 선생님 ID

    @Column(length = 50)
    private String name;

    @Column
    private byte[] password;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String school;

    @Column
    private Timestamp created_at;

    // 조인

    //주식
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Stock> stocks; // 선생님이 관리하는 주식 목록


    //주식
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Etf> etfs; // 선생님이 관리하는 주식 목록

}
