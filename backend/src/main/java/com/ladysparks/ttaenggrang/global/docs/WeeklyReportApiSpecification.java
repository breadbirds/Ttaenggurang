package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.weekly_report.dto.WeeklyReportDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Weekly-Report", description = "주간 금융 리포트 API")
public interface WeeklyReportApiSpecification {

    @Operation(summary = "주간 통계 리포트 [조회]", description = """
          💡 학생 ID를 기반으로 주간 금융 통계를 조회합니다.
          - **총 수입**: 입금, 급여, 판매 수익, 이자, 투자 수익 포함
          - **총 지출**: 출금, 아이템 구매, 투자 비용 포함
          - **총 저축**: 적금 및 예금 이자 포함
          - **총 투자 비용 및 투자 수익**: ETF, 주식 거래 내역 분석
          - **순자산 변화**: 주간 동안의 자산 증가 또는 감소 분석
        """)
    ResponseEntity<ApiResponse<WeeklyReportDTO>> WeeklyReportDetails();

}