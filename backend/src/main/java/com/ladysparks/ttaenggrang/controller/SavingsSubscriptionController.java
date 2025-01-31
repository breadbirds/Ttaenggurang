package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.SavingsSubscriptionApiSpecification;
import com.ladysparks.ttaenggrang.domain.bank.SavingsSubscription;
import com.ladysparks.ttaenggrang.dto.SavingsSubscriptionDTO;
import com.ladysparks.ttaenggrang.service.SavingsSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<SavingsSubscriptionDTO> savingsSubscriptionAdd(@RequestBody SavingsSubscriptionDTO savingsSubscriptionDTO) {
        return ResponseEntity.ok(savingsSubscriptionService.addSavingsSubscription(savingsSubscriptionDTO));
    }

    // 적금 가입 내역 [전체 조회]
    @GetMapping
    public ResponseEntity<List<SavingsSubscriptionDTO>> savingsSubscriptionList(@RequestParam Optional<Long> studentId, @RequestParam Optional<Long> savingsProductId) {
        List<SavingsSubscriptionDTO> savingsSubscriptionDTOList = new ArrayList<>();

        if (studentId.isPresent()) {
            savingsSubscriptionDTOList = savingsSubscriptionService.findSavingsSubscriptions(studentId.get());
        } else if (savingsProductId.isPresent()) {
            savingsSubscriptionDTOList = savingsSubscriptionService.findSavingsSubscriptionsBySavingProductId(savingsProductId.get());
        } else {
            return ResponseEntity.badRequest().build();
        }

        return savingsSubscriptionDTOList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(savingsSubscriptionDTOList);
    }

}
