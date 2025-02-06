package com.ladysparks.ttaenggrang.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ladysparks.ttaenggrang.domain.etf.entity.Etf;
import com.ladysparks.ttaenggrang.domain.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 선생님 ID

    @Column(length = 50)
    private String name;

    @Column
    private String password;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String school;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    // <조인>
    // 학생
//    @OneToMany
//    @JoinColumn(name = "studentId", nullable = false)
//    private List<Student> student;

    //주식
//    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
//    private List<Stock> stocks; // 선생님이 관리하는 주식 목록

    //주식
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Etf> etfs; // 선생님이 관리하는 주식 목록

//    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
//    private List<Etf> etfs; // 선생님이 관리하는 주식 목록

    // 국가 (1:1 관계, Nation 엔티티에서 teacher_id를 관리)
    @JsonIgnore  // 🔹 순환 참조 방지
    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Nation nation;
}
