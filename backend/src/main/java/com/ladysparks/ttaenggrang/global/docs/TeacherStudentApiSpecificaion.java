package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.user.dto.MultipleStudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.SingleStudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Teacher-Student-Management", description = "교사-우리반 관리 기능 API")
public interface TeacherStudentApiSpecificaion {

    @Operation(summary = "학생 계정 단일 생성", description = """
            💡 교사가 학생 계정을 1개만 생성합니다.
            
            - **username** : 학생 ID
            - **password** : 비밀번호
            - **teacher** : 학생의 담임 선생님 정보
            - 교사만 생성 가능
            """)
    @PostMapping("/single-create")
    ResponseEntity<ApiResponse<StudentResponseDTO>> createStudent(@RequestBody @Valid SingleStudentCreateDTO singleStudentCreateDTO);

    @Operation(summary = "학생 계정 복수 생성", description = """
            💡 교사가 여러 개의 학생 계정을 생성합니다.
            
            - **baseId** : 학생 계정의 base ID
            - **studentCount** : 우리반 학생 수
            - **예시**
                - **baseId** : student
                - **studentCount** : 10
                - student1, ..., student10 까지 학생 계정 생성
            - 교사만 생성 가능
            
            """)
    @PostMapping("/quick-create")
    ResponseEntity<ApiResponse<List<StudentResponseDTO>>> createStudents(@RequestBody @Valid MultipleStudentCreateDTO multipleStudentCreateDTO);

    @Operation(summary = "우리반 학생 [전체 조회]", description = """
            💡 교사가 우리 반 전체 학생 목록을 조회합니다.
            
            - **username** : 학생 ID
            - **name** : 학생 실명
            - **profileImage** : 학생 프로필 이미지 경로
            - **teacher** : 학생의 담임 선생님 정보
            - **bankAccount** : 학생의 계좌 정보
            """)
    ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getMyClassStudents();

    @Operation(summary = "우리반 학생 [상세 조회]", description = """
            💡 교사가 특정 학생 정보를 조회합니다.
            
            - **username** : 학생 ID
            - **name** : 학생 실명
            - **profileImage** : 학생 프로필 이미지 경로
            - **teacher** : 학생의 담임 선생님 정보
            - **bankAccount** : 학생의 계좌 정보
            """)
    ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentById(@PathVariable Long studentId);
}
