package com.ladysparks.ttaenggrang.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Data
//@AllArgsConstructor
@JsonIgnoreProperties(value = "{id, token}", allowGetters = true)
public class StudentResponseDTO {
    private Long id;
    private String username;  // 생성된 학생 계정
    private String profileImage;

    private Teacher teacher;
    private BankAccount bankAccount;
    private String token;

    // ✅ 기본 조회용 생성자 (id 없이 username과 password만 받음)
    public StudentResponseDTO(String username) {
        this.username = username;
    }

    // ✅ 학생 정보 조회용 생성자
    public StudentResponseDTO(Long id, String username, byte[] profileImage, Teacher teacher, BankAccount bankAccount, String token) {
        this.id = id;
        this.username = username;
        this.profileImage = (profileImage != null && profileImage.length > 0)
                ? java.util.Base64.getEncoder().encodeToString(profileImage)
                : null;
        this.teacher = teacher;
        this.bankAccount = bankAccount;
        this.token = token;
    }
}
