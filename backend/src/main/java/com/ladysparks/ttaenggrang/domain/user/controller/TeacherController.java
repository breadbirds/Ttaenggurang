package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.global.docs.TeacherApiSpecification;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherLoginDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherSignupDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.domain.user.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController implements TeacherApiSpecification {

    private final TeacherService teacherService;

    // 교사 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<TeacherSignupDTO>> signup(@RequestBody @Valid TeacherSignupDTO teacherSignupDTO) {  //  BindingResult bindingResult
//        // 1️⃣ 유효성 검사 실패 시, 에러 메시지 반환
//        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
//            return ResponseEntity.badRequest().body(errorMessage);
//        }

        // 2️⃣ 회원가입 처리
        TeacherSignupDTO savedTeacher = teacherService.signupTeacher(teacherSignupDTO);
        return ResponseEntity.ok(ApiResponse.success(savedTeacher));
    }

    // 교사 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TeacherLoginDTO>> login(@RequestBody @Valid TeacherLoginDTO teacherLoginDTO) {
        TeacherLoginDTO responseDTO = teacherService.loginTeacher(teacherLoginDTO);
        return ResponseEntity.ok(ApiResponse.success(responseDTO));
    }
}