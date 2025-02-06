package com.ladysparks.ttaenggrang.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginRequestDTO {

    private Long id;

    @NotEmpty(message = "아이디를 입력하세요.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;
}
