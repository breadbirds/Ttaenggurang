package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.user.dto.*;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Teacher-Account", description = "교사 계정 관련 API")
public interface TeacherAccountApiSpecification {

    @Operation(summary = "회원가입", description = "💡 회원가입을 진행합니다. (교사만 접근 가능)")
    @PostMapping("/signup")
    ResponseEntity<ApiResponse<TeacherSignupDTO>> signup(@RequestBody TeacherSignupDTO teacherSignupDTO);

    @Operation(summary = "로그인", description = "💡 교사의 로그인을 진행합니다.")
    @PostMapping("/login")
    ResponseEntity<ApiResponse<TeacherLoginDTO>> login(@RequestBody TeacherLoginDTO teacherLoginDTO);

    @Operation(summary = "교사 목록 조회 (확인용)", description = "💡 가입한 교사의 목록을 조회합니다.")
    @PostMapping("/login")
    ResponseEntity<ApiResponse<List<TeacherResponseDTO>>> getAllTeachers();
}
