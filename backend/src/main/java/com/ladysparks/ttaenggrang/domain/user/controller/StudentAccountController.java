package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.domain.user.dto.StudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentLoginRequestDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentLoginResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.domain.user.service.StudentService;
import com.ladysparks.ttaenggrang.global.docs.StudentAccountApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentAccountController implements StudentAccountApiSpecification {

    private final StudentService studentService;

    // 학생 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<StudentLoginResponseDTO>> loginStudents(@RequestBody @Valid StudentLoginRequestDTO studentLoginDTO) {
        StudentLoginResponseDTO responseDTO = studentService.loginStudent(studentLoginDTO);
        return ResponseEntity.ok(ApiResponse.success(responseDTO));
    }
}
