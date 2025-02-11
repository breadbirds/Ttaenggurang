package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.student.dto.SavingsAchievementDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Student-Dashboard", description = "학생 대시보드 페이지 관련 API")
public interface StudentDashboardApiSpecification {

    @Operation(summary = "학생의 저축 목표 달성률 [조회]", description = """
            💡 특정 학생의 학급 내 저축 목표 달성률 및 목표 달성 순위를 조회합니다.
    
            **[ 응답 필드 ]**
            - **studentId** : 학생 ID
            - **savingsAchievementRate** : 저축 목표 달성률 (단위: %)
            - **rank** : 학생의 목표 달성 순위 (1위부터 시작)
           
            **[ 동작 방식 ]**
            - 먼저 Redis에서 목표 달성률을 조회합니다.
            - 만약 Redis에 값이 없으면 DB에서 계산 후 Redis에 저장합니다.
            """)
    ResponseEntity<ApiResponse<SavingsAchievementDTO>> SavingsAchievementRate();

    @Operation(summary = "목표 달성률 기준 TOP N 학생 목록 [조회]", description = """
            💡 목표 달성률을 기준으로 학급 내 상위 N명의 학생을 조회합니다.
            - N = 0인 경우, 전체 학생 순위 조회
            
            **[ 요청 필드 ]**
            - **topN** : 조회할 상위 N명의 학생 수 (필수)
            
            **[ 응답 필드 ]**
            - **studentId** : 학생 ID
            - **goalAchievement** : 목표 달성률 (%)
            - **rank** : 학생의 목표 달성 순위 (1위부터 시작)
            """)
    ResponseEntity<ApiResponse<List<SavingsAchievementDTO>>> TopStudentList(@RequestParam int topN);

}
