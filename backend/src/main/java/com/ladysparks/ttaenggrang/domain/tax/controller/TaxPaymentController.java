package com.ladysparks.ttaenggrang.domain.tax.controller;

import com.ladysparks.ttaenggrang.domain.tax.dto.TaxDTO;
import com.ladysparks.ttaenggrang.domain.tax.dto.TaxPaymentDTO;
import com.ladysparks.ttaenggrang.domain.tax.service.TaxPaymentService;
import com.ladysparks.ttaenggrang.domain.tax.service.TaxService;
import com.ladysparks.ttaenggrang.global.docs.TaxApiSpecification;
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

    // 특정 학생의 세금 납부 내역 조회
    @GetMapping("/students/{studentId}")
    public ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentsByStudentList(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(taxPaymentService.findTaxPaymentsByStudent(studentId)));
    }

    // 특정 세금 유형에 대한 납부 내역 조회
    @GetMapping("/taxes/{taxId}")
    public ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentsByTaxList(@PathVariable Long taxId) {
        return ResponseEntity.ok(ApiResponse.success(taxPaymentService.findTaxPaymentsByTax(taxId)));
    }

    // 특정 교사의 학생들의 세금 납부 내역 조회
    @GetMapping("/teachers/{teacherId}")
    public ResponseEntity<ApiResponse<List<TaxPaymentDTO>>> taxPaymentsByTeacherList(@PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(taxPaymentService.findTaxPaymentsByTeacher(teacherId)));
    }

    // 세금 납부 등록
    @PostMapping
    public ResponseEntity<ApiResponse<TaxPaymentDTO>> taxPaymentAdd(@RequestBody TaxPaymentDTO requestDTO) {
        return ResponseEntity.ok(ApiResponse.created(taxPaymentService.addTaxPayment(requestDTO)));
    }

}