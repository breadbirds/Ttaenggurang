package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.tax.dto.TaxDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Tag(name = "Tax", description = "세금 관련 API")
public interface TaxApiSpecification {

    @Operation(summary = "세금 [등록]", description = """
            💡 교사가 세금 정보를 등록합니다.
            
            **[ 응답 필드 ]**
            - **taxName** : 세금명
            - **taxRate** : 0 < taxRate(세율) < 1 값만 입력 가능합니다.
            - **taxDescription** : 세금에 대한 설명을 입력합니다.
            """)
    ResponseEntity<ApiResponse<TaxDTO>> taxAdd(@RequestBody @Valid TaxDTO taxDTO);

    @Operation(summary = "세금 [전체 조회]", description = """
            💡 교사가 설정한 국가의 직접 추가한 세금 정보를 조회합니다.
            - teacherId는 생략 가능(생략 시 현재 로그인된 교사 ID로 조회)
            
            **[ 응답 필드 ]**
            - **taxName** : 세금명
            - **taxRate** : 0 < taxRate(세율) < 1 값만 입력 가능합니다.
            - **taxDescription** : 세금에 대한 설명을 입력합니다.
            """)
    ResponseEntity<ApiResponse<List<TaxDTO>>> taxList(@RequestParam Optional<Long> teacherId);

}
