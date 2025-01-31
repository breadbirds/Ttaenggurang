package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.BankTransactionDTO;
import com.ladysparks.ttaenggrang.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Bank-Transaction", description = "은행 계좌 거래 내역 관련 API")
public interface BankTransactionApiSpecification {

    @Operation(summary = "은행 계좌 거래 내역 [전체 조회]", description = "학생 ID로 은행 계좌 거래 내역을 조회합니다.")
    ResponseEntity<ApiResponse<List<BankTransactionDTO>>> bankTransactionList(@RequestParam Long studentId);

}
