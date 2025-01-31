package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.SavingsProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Savings-Product", description = "적금 상품 관련 API")
public interface SavingsProductApiSpecification {

    @Operation(summary = "적금 상품 [등록]", description = "교사가 적금 상품을 등록합니다.")
    ResponseEntity<SavingsProductDTO> savingsProductAdd(SavingsProductDTO savingsProductDTO);

    @Operation(summary = "적금 상품 [조회]", description = "교사 ID로 적금 상품을 조회합니다.")
    ResponseEntity<List<SavingsProductDTO>> savingsProductList(@RequestParam Long teacherId);

}
