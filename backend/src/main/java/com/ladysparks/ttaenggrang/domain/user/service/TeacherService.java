package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.global.config.JwtTokenProvider;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherLoginDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherSignupDTO;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;  // ✅ JWT 토큰 생성기

    // 회원가입
    public TeacherSignupDTO signupTeacher(TeacherSignupDTO teacherSignupDTO) {
        // 중복 이메일 체크
        // 이미 해당 이메일을 가진 Teacher가 존재하는지 확인 후 저장하기
        if (teacherRepository.findByEmail(teacherSignupDTO.getEmail()).isPresent()) {
            throw new IllegalIdentifierException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 일치 여부 확인
        if (!teacherSignupDTO.getPassword1().equals(teacherSignupDTO.getPassword2())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Teacher teacher = Teacher.builder()
                .email(teacherSignupDTO.getEmail())
                .name(teacherSignupDTO.getName())
                .school(teacherSignupDTO.getSchool())
                .password(passwordEncoder.encode(teacherSignupDTO.getPassword2()))  // 비밀번호 암호화
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
        teacherRepository.save(teacher);

        return TeacherSignupDTO.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .name(teacher.getName())
                .school(teacher.getSchool())
                .createdAt(teacher.getCreatedAt())
                .build();
    }

    // 로그인
    public TeacherLoginDTO loginTeacher(TeacherLoginDTO teacherLoginDTO) {
        Teacher teacher = teacherRepository.findByEmail(teacherLoginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(teacherLoginDTO.getPassword(), teacher.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JMT 토큰 생성
        String token = jwtTokenProvider.createToken(teacher.getEmail());

        // 응답을 위한 DTO 생성 (요청용 DTO와 구별)
        return new TeacherLoginDTO(teacher.getEmail(), teacher.getName(), teacher.getSchool(), token);
    }
}
