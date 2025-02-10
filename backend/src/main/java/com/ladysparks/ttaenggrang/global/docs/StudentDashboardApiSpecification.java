package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.student.dto.SavingsAchievementDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Student-Dashboard", description = "학생 대시보드 페이지 관련 API")
public interface StudentDashboardApiSpecification {

    @Operation(summary = "학생의 저축 목표 달성률 [조회]", description = """
            💡 특정 학생의 저축 목표 달성률을 조회합니다.

            - **studentId** : 학생 ID
            - **savingsAchievementRate** : 저축 목표 달성률 (%)
            """)
    ResponseEntity<ApiResponse<SavingsAchievementDTO>> SavingsAchievementRate();

}
