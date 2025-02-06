package com.ladysparks.ttaenggrang.domain.bank.controller;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsDepositDTO;
import com.ladysparks.ttaenggrang.domain.bank.service.SavingsDepositService;
import com.ladysparks.ttaenggrang.global.docs.SavingsDepositApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings-deposits")
@RequiredArgsConstructor
public class SavingsDepositController implements SavingsDepositApiSpecification {

    private final SavingsDepositService savingsDepositService;

    // 적금 납입 내역 추가
    @PostMapping
    public ResponseEntity<ApiResponse<SavingsDepositDTO>> SavingsDepositAdd(@RequestBody SavingsDepositDTO savingsDepositDTO) {
        SavingsDepositDTO savedDeposit = savingsDepositService.addSavingsDeposit(savingsDepositDTO);
        return ResponseEntity.ok(ApiResponse.success(savedDeposit));
    }

    // 특정 적금 가입의 납입 내역 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<SavingsDepositDTO>>> SavingsDepositList(@RequestParam Long savingsSubscriptionId) {
        List<SavingsDepositDTO> deposits = savingsDepositService.findSavingsDeposits(savingsSubscriptionId);
        return ResponseEntity.ok(ApiResponse.success(deposits));
    }

}