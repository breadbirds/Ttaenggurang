package com.ladysparks.ttaenggrang.domain.tax.service;

import com.ladysparks.ttaenggrang.domain.tax.dto.TaxPaymentDTO;
import com.ladysparks.ttaenggrang.domain.tax.entity.Tax;
import com.ladysparks.ttaenggrang.domain.tax.entity.TaxPayment;
import com.ladysparks.ttaenggrang.domain.tax.mapper.TaxPaymentMapper;
import com.ladysparks.ttaenggrang.domain.tax.repository.TaxPaymentRepository;
import com.ladysparks.ttaenggrang.domain.tax.repository.TaxRepository;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import com.ladysparks.ttaenggrang.domain.user.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxPaymentService {

    private final TaxPaymentRepository taxPaymentRepository;
    private final TaxRepository taxRepository;
    private final TaxPaymentMapper taxPaymentMapper;

    public TaxPaymentService(TaxPaymentRepository taxPaymentRepository,
                             TaxRepository taxRepository,
                             TaxPaymentMapper taxPaymentMapper,
                             StudentService studentService) {
        this.taxPaymentRepository = taxPaymentRepository;
        this.taxRepository = taxRepository;
        this.taxPaymentMapper = taxPaymentMapper;
    }

    // 특정 학생의 세금 납부 내역 조회
    public List<TaxPaymentDTO> findTaxPaymentsByStudent(Long studentId) {
        return taxPaymentRepository.findByStudentId(studentId).stream()
                .map(taxPaymentMapper::toDto)
                .collect(Collectors.toList());
    }

    // 특정 세금 유형에 대한 납부 내역 조회
    public List<TaxPaymentDTO> findTaxPaymentsByTax(Long taxId) {
        return taxPaymentRepository.findByTaxId(taxId).stream()
                .map(taxPaymentMapper::toDto)
                .collect(Collectors.toList());
    }

    // 특정 교사가 담당하는 학생들의 세금 납부 내역 조회
    public List<TaxPaymentDTO> findTaxPaymentsByTeacher(Long teacherId) {
        return taxPaymentRepository.findByTeacherId(teacherId).stream()
                .map(taxPaymentMapper::toDto)
                .collect(Collectors.toList());
    }

    // 세금 납부 등록
    @Transactional
    public TaxPaymentDTO addTaxPayment(TaxPaymentDTO taxPaymentDTO) {
        Tax tax = taxRepository.findById(taxPaymentDTO.getTaxId())
                .orElseThrow(() -> new IllegalArgumentException("세금 정보를 찾을 수 없습니다."));

        TaxPayment taxPayment = taxPaymentMapper.toEntity(taxPaymentDTO);
        TaxPayment savedTaxPayment = taxPaymentRepository.save(taxPayment);

        return taxPaymentMapper.toDto(savedTaxPayment);
    }

}
