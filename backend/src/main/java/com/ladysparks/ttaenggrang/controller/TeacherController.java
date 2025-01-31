package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.TeacherApiSpecification;
import com.ladysparks.ttaenggrang.dto.TeacherLoginDTO;
import com.ladysparks.ttaenggrang.dto.TeacherSignupDTO;
import com.ladysparks.ttaenggrang.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController implements TeacherApiSpecification {

    private final TeacherService teacherService;

    // 교사 회원가입
    @PostMapping("/signup")
    public ResponseEntity<TeacherSignupDTO> signup(@RequestBody @Valid TeacherSignupDTO teacherSignupDTO) {  //  BindingResult bindingResult
//        // 1️⃣ 유효성 검사 실패 시, 에러 메시지 반환
//        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
//            return ResponseEntity.badRequest().body(errorMessage);
//        }

        // 2️⃣ 회원가입 처리
        TeacherSignupDTO savedTeacher = teacherService.signupTeacher(teacherSignupDTO);
        return ResponseEntity.ok(savedTeacher);
    }

    // 교사 로그인
    @PostMapping("/login")
    public ResponseEntity<TeacherLoginDTO> login(@RequestBody @Valid TeacherLoginDTO teacherLoginDTO) {
        TeacherLoginDTO responseDTO = teacherService.loginTeacher(teacherLoginDTO);
        return ResponseEntity.ok(responseDTO);
    }
}