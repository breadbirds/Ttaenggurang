package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.domain.user.Teacher;
import com.ladysparks.ttaenggrang.dto.TeacherLoginDTO;
import com.ladysparks.ttaenggrang.dto.TeacherSignupDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Teacher", description = "교사 계정 관련 API")
@RequestMapping("/teachers")
public interface TeacherApiSpecification {

    @Operation(summary = "회원가입", description = "💡 회원가입을 진행합니다. (교사만 접근 가능)")
    @PostMapping("/signup")
    ResponseEntity<TeacherSignupDTO> signup(@RequestBody TeacherSignupDTO teacherSignupDTO);

    @Operation(summary = "로그인", description = "💡 교사의 로그인을 진행합니다.")
    @PostMapping("/login")
    ResponseEntity<TeacherLoginDTO> login(@RequestBody TeacherLoginDTO teacherLoginDTO);

}
