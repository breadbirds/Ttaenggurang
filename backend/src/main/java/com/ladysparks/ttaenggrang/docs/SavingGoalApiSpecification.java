package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.SavingsGoalDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Savings-Goal", description = "저축 목표 관련 API")
public interface SavingGoalApiSpecification {

    @Operation(summary = "저축 목표 [등록]", description = "학생이 저축 목표를 등록합니다.")
    public ResponseEntity<SavingsGoalDTO> savingGoalAdd(@RequestBody SavingsGoalDTO savingsGoalDTO);

    @Operation(summary = "저축 목표 [전체 조회]", description = "학생의 저축 목표를 조회합니다.")
    public ResponseEntity<List<SavingsGoalDTO>> savingGoalList(@RequestParam Long studentId);

}
