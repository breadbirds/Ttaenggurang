package com.ladysparks.ttaenggrang.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Nation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nationName;

    @Column(nullable = false)
    private Integer population;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Integer savingsGoalAmount;

    @Column(nullable = false)
    private Timestamp establishedDate;

    @JsonIgnore  // 순환 참조 방지
    @OneToOne
    @JoinColumn(name = "teacher_id", unique = true, nullable = false)
    private Teacher teacher;
}
