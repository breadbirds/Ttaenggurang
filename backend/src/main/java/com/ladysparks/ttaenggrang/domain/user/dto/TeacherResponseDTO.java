package com.ladysparks.ttaenggrang.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO {
    private Long id;
    private String email;
    private String name;
    private String school;
    private Timestamp createdAt;
}
