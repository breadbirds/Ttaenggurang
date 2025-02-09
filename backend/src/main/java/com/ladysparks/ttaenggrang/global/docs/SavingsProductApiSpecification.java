package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsProductDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Savings-Product", description = "적금 상품 관련 API")
public interface SavingsProductApiSpecification {

    @Operation(summary = "적금 상품 [등록]", description = """
            💡 교사가 적금 상품을 등록합니다.

            **[ 필드 설명 ]**
            - **name** : 적금 상품명
            - **interestRate** : 이자율
            - **earlyInterestRate** : 중도 해지시 적용되는 이자율
            - **durationWeeks** : 가입 기간 (주 단위)
            - **amount** : 적금 금액 (주마다 납입하는 금액)
            - **saleStartDate** : 노출 시작일
            - **saleEndDate** : 노출 종료일
            
            **[ 규칙 ]**
            - 같은 교사가 같은 이름의 적금 상품을 등록할 수 없습니다.
            - 이자율의 범위는 0.0 ~ 100.0 (%)
            """)
    ResponseEntity<ApiResponse<SavingsProductDTO>> savingsProductAdd(SavingsProductDTO savingsProductDTO);

    @Operation(summary = "적금 상품 [조회]", description = """
            💡 교사가 등록한 전체 적금 상품을 조회합니다.

            - **name** : 적금 상품명
            - **interestRate** : 이자율
            - **earlyInterestRate** : 중도 해지시 적용되는 이자율
            - **durationWeeks** : 가입 기간 (주 단위)
            - **amount** : 적금 금액
            - **saleStartDate** : 노출 시작일
            - **saleEndDate** : 노출 종료일
            """)
    ResponseEntity<ApiResponse<List<SavingsProductDTO>>> savingsProductList();

}
