package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlin.OptionalExpectation;
import org.springframework.http.ResponseEntity;

@Tag(name = "Salary", description = "지급 관련 API")
public interface SalaryApiSpecification {

    @Operation(summary = "주급 지급", description = """
            교사가 '주급 지급' 버튼을 클릭하면, 각 학생의 직업에 따라 설정된 기본급(`baseSalary`)이 학생들의 계좌로 자동으로 지급됩니다.
    
            - **대상:** 교사의 반에 등록된 모든 학생
            - **급여 기준:** 학생이 가진 직업(`job`)의 기본급(`baseSalary`)
            - **결과:** 지급된 금액이 학생들의 계좌(`BankAccount`) 잔액에 반영됨
    
            ⚠️ **주의:** 이미 지급된 주급에 대한 중복 지급은 방지됩니다.
            - 주급은 지급일 기준, **최소 일주일 후** 지급 가능
            
            - **에러 유형**
                - `"이미 이번 주 주급이 지급되었습니다."` : BAD_REQUEST
                - `"해당 교사를 찾을 수 없습니다."` : 교사 유효성 검증
                - `"우리 반 학생이 없습니다."` : NOT_FOUND
                - `"주급이 성공적으로 지급되었습니다."` : success
            """)
    ResponseEntity<ApiResponse<String>> distributeBaseSalary();
}
