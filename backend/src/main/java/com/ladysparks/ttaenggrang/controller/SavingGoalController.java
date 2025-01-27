package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.SavingGoalApiSpecification;
import com.ladysparks.ttaenggrang.domain.bank.SavingsGoal;
import com.ladysparks.ttaenggrang.dto.SavingsGoalDTO;
import com.ladysparks.ttaenggrang.service.SavingsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings-goals")
public class SavingGoalController implements SavingGoalApiSpecification {

    private final SavingsGoalService savingsGoalService;

    @Autowired
    public SavingGoalController(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    // 저축 목표 [등록]
    @PostMapping
    public ResponseEntity<SavingsGoalDTO> savingGoalAdd(@RequestBody SavingsGoalDTO savingsGoalDTO) {
        return ResponseEntity.ok(savingsGoalService.addSavingsGoal(savingsGoalDTO));
    }

    // 저축 목표 [전체 조회]
    @GetMapping
    public ResponseEntity<List<SavingsGoalDTO>> savingGoalList(@RequestParam Long studentId) {
        List<SavingsGoalDTO> savingsGoalDTOList = savingsGoalService.findSavingsGoals(studentId);
        return savingsGoalDTOList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(savingsGoalDTOList);
    }

}
