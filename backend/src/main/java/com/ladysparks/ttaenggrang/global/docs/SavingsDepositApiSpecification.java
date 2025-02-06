package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsDepositDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Savings-Deposit", description = "적금 납입 내역 관련 API")
public interface SavingsDepositApiSpecification {

    @Operation(summary = "적금 납입 내역 [등록]", description = "학생의 특정 적금 상품에 대한 납입 정보를 기록합니다.")
    ResponseEntity<ApiResponse<SavingsDepositDTO>> SavingsDepositAdd(@RequestBody SavingsDepositDTO savingsDepositDTO);

    @Operation(summary = "적금 납입 내역 [조회]", description = "특정 적금 가입 ID로 납입 내역을 조회합니다.")
    ResponseEntity<ApiResponse<List<SavingsDepositDTO>>> SavingsDepositList(@RequestParam Long savingsSubscriptionId);

}
