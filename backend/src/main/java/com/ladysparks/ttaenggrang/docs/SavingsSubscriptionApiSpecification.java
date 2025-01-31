package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.SavingsSubscriptionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Tag(name = "Saving-Subscription", description = "적금 가입 내역 관련 API")
public interface SavingsSubscriptionApiSpecification {

    @Operation(summary = "적금 가입 [등록]", description = "학생이 적금 상품을 가입합니다.")
    ResponseEntity<SavingsSubscriptionDTO> savingsSubscriptionAdd(@RequestBody SavingsSubscriptionDTO savingsSubscriptionDTO);

    @Operation(summary = "적금 가입 학생 내역 [전체 조회]", description = "학생 ID or 적금 상품 ID로 적금 가입 내역을 조회합니다.")
    ResponseEntity<List<SavingsSubscriptionDTO>> savingsSubscriptionList(@RequestParam Optional<Long> studentId, @RequestParam Optional<Long> savingsProductId);

}
