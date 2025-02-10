package com.ladysparks.ttaenggrang.domain.tax.controller;

import com.ladysparks.ttaenggrang.domain.tax.dto.TaxPaymentDTO;
import com.ladysparks.ttaenggrang.domain.tax.service.TaxPaymentService;
import com.ladysparks.ttaenggrang.global.docs.TaxPaymentApiSpecification;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tax-payments/")
@RequiredArgsConstructor
public class TaxPaymentController implements TaxPaymentApiSpecification {

    private final TaxPaymentService taxPaymentService;

    // 세금 납부 등록
    @PostMapping
    public ResponseEntity<ApiResponse<TaxPaymentDTO>> taxPaymentAdd(@RequestBody @Valid TaxPaymentDTO requestDTO) {
        return ResponseEntity.ok(ApiResponse.created(taxPaymentService.addTaxPayment(requestDTO)));
    }

    // 특정 학생의 세금 납부 내역 조회
    @GetMapping("/student")
    public ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentListByStudent() {
        return ResponseEntity.ok(ApiResponse.success(taxPaymentService.findTaxPaymentsByStudent()));
    }

    // 특정 세금 유형에 대한 납부 내역 조회
    @GetMapping("/taxes/{taxId}")
    public ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentListByTax(@PathVariable Long taxId) {
        return ResponseEntity.ok(ApiResponse.success(taxPaymentService.findTaxPaymentsByTax(taxId)));
    }

    // 특정 교사의 학생들의 세금 납부 내역 조회
    @GetMapping("/teacher")
    public ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentListByTeacher() {
        return ResponseEntity.ok(ApiResponse.success(taxPaymentService.findTaxPaymentsByTeacher()));
    }

}