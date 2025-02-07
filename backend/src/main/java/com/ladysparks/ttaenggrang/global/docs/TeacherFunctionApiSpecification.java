package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.tax.dto.TaxCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.*;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Teacher-Function", description = "교사 관리 기능 API")
public interface TeacherFunctionApiSpecification {

    @Operation(summary = "직업 [등록]", description = "💡 교사가 새로운 직업을 등록합니다.")
    ResponseEntity<ApiResponse<JobCreateDTO>> createJob(@RequestBody @Valid JobCreateDTO jobCreateDTO);

    @Operation(summary = "국가 [등록]", description = "💡 교사가 국가 정보를 등록합니다. (계정 당 1개의 국가만 생성할 수 있습니다.)")
    ResponseEntity<ApiResponse<NationCreateDTO>> createNation(@RequestBody @Valid NationCreateDTO nationCreateDTO);

    @Operation(summary = "국가 [조회]", description = "💡 교사가 국가 정보를 조회합니다.")
    ResponseEntity<ApiResponse<NationCreateDTO>> getNationByTeacher();

    @Operation(summary = "직업 [학생 목록 전체 조회]", description = "💡 해당 직업을 가진 학생 전체 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getStudentsByJobId(@PathVariable Long jobId);
}
