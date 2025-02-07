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

    @Operation(summary = "학생의 세금 납부 내역 조회", description = "특정 학생의 세금 납부 내역을 조회합니다.")
    ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentsByStudentList(@RequestParam Long studentId);

    @Operation(summary = "세금 유형별 납부 내역 조회", description = "특정 세금 유형에 대한 납부 내역을 조회합니다.")
    ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentsByTaxList(@RequestParam Long taxId);

    @Operation(summary = "교사 관리 학생 세금 납부 내역 조회", description = "특정 교사가 담당하는 학생들의 세금 납부 내역을 조회합니다.")
    ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentsByTeacherList(@RequestParam Long teacherId);

    @Operation(summary = "세금 납부 등록", description = "학생이 세금을 납부할 수 있도록 등록합니다.")
    ResponseEntity<ApiResponse<TaxPaymentDTO>> taxPaymentAdd(@RequestBody TaxPaymentDTO requestDTO);

}