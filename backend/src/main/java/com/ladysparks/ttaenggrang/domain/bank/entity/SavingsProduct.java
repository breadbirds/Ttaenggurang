package com.ladysparks.ttaenggrang.domain.bank.entity;

import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

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
    private int amount;

    @Column(nullable = false)
    private LocalDate saleStartDate;

    @Column(nullable = false)
    private LocalDate saleEndDate;

    @CreationTimestamp
    private Timestamp createdAt;

}
