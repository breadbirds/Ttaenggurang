package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.domain.user.dto.*;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.domain.user.service.JobService;
import com.ladysparks.ttaenggrang.domain.user.service.NationService;
import com.ladysparks.ttaenggrang.domain.user.service.StudentService;
import com.ladysparks.ttaenggrang.domain.user.service.TaxService;
import com.ladysparks.ttaenggrang.global.docs.TeacherFunctionApiSpecification;
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
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherFunctionController implements TeacherFunctionApiSpecification {

    private final JobService jobService;
    private final NationService nationService;
    private final TaxService taxService;
    private final StudentService studentService;
    private final TeacherRepository teacherRepository;

    // (+) 현재 로그인한 교사의 ID 가져오는 메서드
    private long getTeacherIdFromSecurityContext() {
        // 🔥 인증 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("★인증 객체: " + authentication);

        // ✅ 인증 정보가 없을 경우 예외 발생 방지
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다. 로그인 후 다시 시도하세요.");
        }

        // Principal 가져오기 : SecurityContextHolder에서 인증된 사용자 정보 가져오기
        Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principalObj instanceof UserDetails) {
            String email = ((UserDetails) principalObj).getUsername();
            return teacherRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 교사를 찾을 수 없습니다."))
                    .getId();
        }
        throw new IllegalArgumentException("현재 인증된 사용자를 찾을 수 없습니다.");
    }

    // 직업 [등록]
    @PostMapping("/jobs/create")
    public ResponseEntity<ApiResponse<JobCreateDTO>> createJob(@RequestBody @Valid JobCreateDTO jobCreateDTO) {
        ApiResponse<JobCreateDTO> response = jobService.createJob(jobCreateDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 직업 [학생 목록 전체 조회]
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getStudentsByJobId(@PathVariable Long jobId) {
        ApiResponse<List<StudentResponseDTO>> response = studentService.getStudentsByJobId(jobId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 국가 정보 [등록]
    @PostMapping("/nations/create")
    public ResponseEntity<ApiResponse<NationCreateDTO>> createNation(@RequestBody @Valid NationCreateDTO nationCreateDTO) {

        // 로그인한 교사의 ID 가져오기
        Long teacherId = getTeacherIdFromSecurityContext();

        ApiResponse<NationCreateDTO> response = nationService.createNation(teacherId, nationCreateDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 국가 정보 [조회]
    @GetMapping("/nations")
    public ResponseEntity<ApiResponse<NationCreateDTO>> getNationByTeacher() {
        // 현재 로그인한 교사 ID 가져오기
        long teacherId = getTeacherIdFromSecurityContext();

        ApiResponse<NationCreateDTO> response = nationService.getNationByTeacherId(teacherId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 세금 항목 [등록]
    @PostMapping("/taxes/create")
    public ResponseEntity<ApiResponse<TaxCreateDTO>> createTax(@RequestBody @Valid TaxCreateDTO taxCreateDTO) {
        ApiResponse<TaxCreateDTO> response = taxService.createTax(taxCreateDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 세금 항목 [전체 조회]
    @GetMapping("/taxes")
    public ResponseEntity<ApiResponse<List<TaxCreateDTO>>> getAllTaxes() {
        ApiResponse<List<TaxCreateDTO>> response = taxService.getAllTaxes();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
