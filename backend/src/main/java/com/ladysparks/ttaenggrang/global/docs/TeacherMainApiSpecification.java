package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.bank.dto.StudentDailyAverageFinancialDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Teacher-Main", description = "교사 메인 페이지 관련 API")
public interface TeacherMainApiSpecification {

    @Operation(
            summary = "교사의 학생 평균 수입 및 지출 조회",
            description = "교사 ID를 기반으로 담당하는 학생들의 최근 7일 평균 수입과 평균 지출을 반환합니다."
    )
    ResponseEntity<List<StudentDailyAverageFinancialDTO>> getDailyAverageIncomeAndExpense();

}
