package com.ladysparks.ttaenggrang.domain.nation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private int nationalTreasury;

    @Column(nullable = false)
    private Timestamp establishedDate;

}
