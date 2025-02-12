package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsPayoutDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Savings-Payout", description = "적금 지급 내역 관리 API")
public interface SavingsPayoutApiSpecification {

    @Operation(summary = "적금 지급 내역 [조회]", description = """
            💡 특정 적금 가입 정보에 대한 지급 내역을 조회합니다.
            
            ---
            
            **[ 요청 값 ]**
            - **savingsSubscriptionId** : 조회할 적금 가입 ID
            
            **[ 응답 필드 ]**
            - **id** : 적금 지급 ID
            - **savingsSubscriptionId** : 적금 가입 ID
            - **payoutAmount** : 지급 금액
            - **interestAmount** : 지급된 이자 금액
            - **payoutDate** : 지급일
            - **payoutType** : 지급 유형
                - 만기 지급 → **MATURITY**
                - 중도 인출 → **EARLY_WITHDRAWAL**
            - **createdAt** : 지급 내역 생성일
            
            ---
            
            **[ 동작 방식 ]**
            - 지정된 `savingsSubscriptionId`에 대한 지급 내역을 조회합니다.
            - 지급 유형에는 `만기 지급(Maturity)`과 `중도 인출(Early Withdrawal)`이 포함됩니다.
            
            """)
    ResponseEntity<ApiResponse<SavingsPayoutDTO>> savingsPayoutDetails(@Parameter(description = "조회할 적금 가입 ID", example = "1") @RequestParam Long savingsSubscriptionId);

}
