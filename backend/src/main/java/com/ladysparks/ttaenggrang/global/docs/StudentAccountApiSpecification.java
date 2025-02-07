package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.user.dto.*;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Student-Account", description = "학생 계정 관련 API")
public interface StudentAccountApiSpecification {

    @Operation(summary = "학생 로그인", description = """
            💡 학생의 로그인을 진행합니다.
            
            - username : 로그인 시 사용되는 학생 ID (교사가 생성)
            - name : 학생의 실명
            """)
    @PostMapping("/login")
    ResponseEntity<ApiResponse<StudentLoginResponseDTO>> loginStudents(@RequestBody @Valid StudentLoginRequestDTO studentLoginDTO);

    @Operation(summary = "학생 로그아웃", description = "💡 학생 계정을 로그아웃합니다.")
    @PostMapping("/logout")
    ResponseEntity<ApiResponse<String>> logoutStudent(HttpServletRequest request);

    @Operation(summary = "학생 목록 조회 (확인용)", description = "💡 가입한 학생들의 목록을 조회합니다.")
    @PostMapping("/all")
    ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getAllStudents();
}
