package com.ladysparks.ttaenggrang.domain.bank.entity;

import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SavingsProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private float interestRate;

    @Column(nullable = false)
    private float earlyInterestRate;

    @Column(nullable = false)
    private int durationWeeks;

    @Column(nullable = false)
    private int depositAmount;

    @Column(nullable = false)
    private Date saleStartDate;

    @Column(nullable = false)
    private Date saleEndDate;

    @CreationTimestamp
    private Timestamp createdAt;

}
