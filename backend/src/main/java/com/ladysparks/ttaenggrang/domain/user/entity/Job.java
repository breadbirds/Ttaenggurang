package com.ladysparks.ttaenggrang.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String jobName;

    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private Integer baseSalary;

    @Column(nullable = false)
    private Integer maxPeople;

    @Column(nullable = false)
    private Timestamp salaryDate;

    @PrePersist
    protected void onCreate() {
        this.salaryDate = new Timestamp(System.currentTimeMillis());  // 기본값 설정
    }

    @OneToOne
    @JoinColumn(name = "job_id")  // nullable = false
    private Student student;

}
