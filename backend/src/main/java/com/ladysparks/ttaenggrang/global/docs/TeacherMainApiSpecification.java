package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.bank.dto.StudentDailyAverageFinancialDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherDashboardDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Teacher-Main", description = "교사 메인 페이지 관련 API")
public interface TeacherMainApiSpecification {

    @Operation(summary = "교사가 학생 평균 수입 및 지출 조회", description = """
            💡 교사가 담당하는 학생들의 최근 7일 평균 수입과 평균 지출을 반환합니다.
            
            - **date** : 날짜
            - **averageIncome** : 평균 수입
            - **averageExpense** : 평균 지출
            """)
    ResponseEntity<ApiResponse<List<StudentDailyAverageFinancialDTO>>> DailyAverageIncomeAndExpense();

    @Operation(summary = "교사 메인 대시보드 조회", description = """
            💡 교사 ID를 기반으로 메인 대시보드 정보를 조회합니다.

            - **treasuryIncome** : 국고 수입
            - **averageStudentBalance** : 1인 평균 잔고
            - **activeItemCount** : 판매 중인 상품 개수
            - **classSavingsGoal** : 우리 반 목표 저축액
            """)
    ResponseEntity<ApiResponse<TeacherDashboardDTO>> TeacherDashboardDetails();

}
