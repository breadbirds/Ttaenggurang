package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.user.dto.StudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherLoginDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherSignupDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.sql.ast.tree.expression.Summarization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.DigestException;
import java.util.List;

@Tag(name = "Student", description = "학생 계정 관련 API")
@RequestMapping("/students")
public interface StudentApiSpecification {

    @Operation(summary = "학생 계정 생성", description = "💡 교사가 학생 계정을 생성합니다. (교사만 생성 가능)")
    @PostMapping("/create")
    ResponseEntity<ApiResponse<List<StudentResponseDTO>>> createStudents(@RequestBody StudentCreateDTO studentCreateDTO);
}
