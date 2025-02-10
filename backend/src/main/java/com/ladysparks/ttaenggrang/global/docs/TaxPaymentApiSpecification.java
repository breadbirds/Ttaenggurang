package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.tax.dto.TaxPaymentDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Tax-Payment", description = "세금 납부 내역 관련 API")
public interface TaxPaymentApiSpecification {

    @Operation(summary = "세금 납부 [등록]", description = """
            💡 학생이 세금을 납부합니다.
            
            **[ 필드 설명 ]**
            - **taxId** : 세금 ID
            - **amount** : 납부 금액
            - **status**: 납부 상태
                - **"COMPLETED"** : 납부 완료
                - **"FAILED"** : 체납
                - **"PENDING"** : 예정됨
            
            **[ 규칙 ]**
            - 세금 ID, 납부 금액, 납부 상태는 필수 항목입니다.
            - 납부 금액은 1 이상이어야 합니다.
            """)
    ResponseEntity<ApiResponse<TaxPaymentDTO>> taxPaymentAdd(@RequestBody TaxPaymentDTO requestDTO);

    @Operation(summary = "학생 세금 납부 내역 (학생) [조회]", description = """
            💡 특정 학생의 세금 납부 내역을 조회합니다.
            
            - **id** : 세금 납부 ID
            - **studentId** : 학생 ID
            - **taxId** : 세금 ID
            - **amount** : 납부 금액
            - **paymentDate** : 납부일
            - **status** : 납부 상태
            - **createdAt** : 납부 생성일
            """)
    ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentListByStudent();

    @Operation(summary = "세금 유형별 납부 내역 [조회]", description = """
            💡 학급 내 특정 세금 유형에 대한 납부 내역을 조회합니다.
            
            - **id** : 세금 납부 ID
            - **studentId** : 학생 ID
            - **taxId** : 세금 ID
            - **amount** : 납부 금액
            - **paymentDate** : 납부일
            - **status** : 납부 상태
            - **createdAt** : 납부 생성일
            """)
    ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentListByTax(@RequestParam Long taxId);

    @Operation(summary = "학생 세금 납부 내역 (교사) [조회]", description = """
            💡 특정 교사가 담당하는 학생들의 세금 납부 내역을 조회합니다.
            
            - **id** : 세금 납부 ID
            - **studentId** : 학생 ID
            - **taxId** : 세금 ID
            - **amount** : 납부 금액
            - **paymentDate** : 납부일
            - **status** : 납부 상태
            - **createdAt** : 납부 생성일
            """)
    ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentListByTeacher();

}