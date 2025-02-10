package com.ladysparks.ttaenggrang.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAchievementDTO {

    private Long studentId;
    private double savingsAchievementRate;

}