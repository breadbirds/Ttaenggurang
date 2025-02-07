package com.ladysparks.ttaenggrang.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"id", "profileImage", "teacher", "bankAccount", "token"}, allowGetters=true)
public class SingleStudentCreateDTO {
    private int id;

    @NotEmpty(message = "ID를 입력하세요.")
    private String username;

    @NotEmpty(message="비밀번호는 필수항목입니다.")
    private String password;
    private String profileImage;

    private Teacher teacher;
    private BankAccount bankAccount;
    private Student token;
}
