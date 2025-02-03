package com.ladysparks.ttaenggrang.domain.user.controller;

import com.ladysparks.ttaenggrang.domain.user.dto.StudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.domain.user.service.StudentService;
import com.ladysparks.ttaenggrang.global.docs.StudentApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController implements StudentApiSpecification {

    private final StudentService studentService;
    private final TeacherRepository teacherRepository;

    // 학생 계정 생성 (교사만 가능)  (토큰 문제 해결 후 다시 사용하기)
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> createStudents(
            @RequestBody @Valid StudentCreateDTO studentCreateDTO) {

        // ✅ 현재 로그인한 교사의 ID 가져오기
        Long teacherId = getTeacherIdFromSecurityContext();

        // ✅ 학생 계정 생성 서비스 호출
        List<StudentResponseDTO> createdStudents = studentService.createStudentAccounts(teacherId, studentCreateDTO);

        // ✅ ApiResponse.success() 사용
        return ResponseEntity.ok(ApiResponse.success(createdStudents));
    }

    // (+) 현재 로그인한 교사의 ID 가져오는 메서드
    private long getTeacherIdFromSecurityContext() {
        // 🔥 인증 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("★인증 객체: " + authentication);

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

    // 학생 계정 생성 (로그인 없이)
//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> createStudents(
//            @RequestBody @Valid StudentCreateDTO studentCreateDTO) {
//
//        // ✅ 현재 로그인한 교사의 ID 가져오기
//        Long teacherId = getTeacherIdFromSecurityContext();
//
//        // ✅ 학생 계정 생성 서비스 호출
//        List<StudentResponseDTO> createdStudents = studentService.createStudentAccounts(teacherId, studentCreateDTO);
//
//        // ✅ ApiResponse.success() 사용
//        return ResponseEntity.ok(ApiResponse.success(createdStudents));
//    }

}
