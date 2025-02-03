package com.ladysparks.ttaenggrang.domain.bank.controller;

import com.ladysparks.ttaenggrang.global.docs.SavingsSubscriptionApiSpecification;
import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsSubscriptionDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.domain.bank.service.SavingsSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/savings-subscriptions")
public class SavingsSubscriptionController implements SavingsSubscriptionApiSpecification {

    private final SavingsSubscriptionService savingsSubscriptionService;

    @Autowired
    public SavingsSubscriptionController(SavingsSubscriptionService savingsSubscriptionService) {
        this.savingsSubscriptionService = savingsSubscriptionService;
    }

    // 적금 가입 [등록]
    @PostMapping
    public ResponseEntity<ApiResponse<SavingsSubscriptionDTO>> savingsSubscriptionAdd(@RequestBody SavingsSubscriptionDTO savingsSubscriptionDTO) {
        SavingsSubscriptionDTO savesSavingsSubscriptionDTO = savingsSubscriptionService.addSavingsSubscription(savingsSubscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(savesSavingsSubscriptionDTO));
    }

    // 적금 가입 (학생) 내역 [전체 조회]
    @GetMapping
    public ResponseEntity<ApiResponse<List<SavingsSubscriptionDTO>>> savingsSubscriptionList(
            @RequestParam Optional<Long> studentId,
            @RequestParam Optional<Long> savingsProductId) {
        return studentId.map(sId -> ResponseEntity.ok(ApiResponse.success(savingsSubscriptionService.findSavingsSubscriptions(sId))))
                .orElseGet(() -> savingsProductId.map(spId -> ResponseEntity.ok(ApiResponse.success(savingsSubscriptionService.findSavingsSubscriptionsBySavingProductId(spId))))
                .orElseGet(() -> ResponseEntity.badRequest().body(ApiResponse.error(400, "studentId 또는 savingsProductId가 필요합니다.", null)))); // ✅ 잘못된 요청 (둘 다 없는 경우) → 400 Bad Request
    }

}
