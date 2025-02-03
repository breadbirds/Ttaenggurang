package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.domain.user.dto.JobCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.NationCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.service.JobService;
import com.ladysparks.ttaenggrang.domain.user.service.NationService;
import com.ladysparks.ttaenggrang.global.docs.TeacherApiSpecification;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherLoginDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherSignupDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.domain.user.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.internal.OptionalDeterminationSecondPass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController implements TeacherApiSpecification {

    private final TeacherService teacherService;
    private final JobService jobService;
    private final NationService nationService;

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

    // 직업 등록
    @PostMapping("/jobs/create")
    @Override
    public ResponseEntity<ApiResponse<?>> createJob(@RequestBody @Valid JobCreateDTO jobCreateDTO) {
        ApiResponse<?> response = jobService.createJob(jobCreateDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 국가 정보 등록
    @PostMapping("/nations/create")
    public ResponseEntity<ApiResponse<?>> createNation(@RequestBody @Valid NationCreateDTO nationCreateDTO) {
        ApiResponse<?> response = nationService.createNation(nationCreateDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}