package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.SavingsProductApiSpecification;
import com.ladysparks.ttaenggrang.dto.SavingsProductDTO;
import com.ladysparks.ttaenggrang.service.SavingsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings-products")
public class SavingsProductController implements SavingsProductApiSpecification {

    private final SavingsProductService savingsProductService;

    @Autowired
    public SavingsProductController(SavingsProductService savingsProductService) {
        this.savingsProductService = savingsProductService;
    }

    // 적금 상품 [등록]
    @PostMapping
    public ResponseEntity<SavingsProductDTO> savingsProductAdd(@RequestBody SavingsProductDTO savingsProductDTO) {
        return ResponseEntity.ok(savingsProductService.addSavingsProducts(savingsProductDTO));
    }

    // 적금 상품 [조회]
    @GetMapping
    public ResponseEntity<List<SavingsProductDTO>> savingsProductList(@RequestParam Long teacherId) {
        List<SavingsProductDTO> savingsProductDTOList = savingsProductService.findSavingsProducts(teacherId);
        return savingsProductDTOList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(savingsProductDTOList);
    }

}
