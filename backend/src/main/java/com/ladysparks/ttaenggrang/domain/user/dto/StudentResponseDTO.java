package com.ladysparks.ttaenggrang.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResponseDTO {
    private String username;  // 생성된 학생 계정
    private String password;  // 초기 비밀번호
}
