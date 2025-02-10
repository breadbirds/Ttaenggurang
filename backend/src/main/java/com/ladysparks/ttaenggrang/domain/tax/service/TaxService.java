package com.ladysparks.ttaenggrang.domain.tax.service;

import com.ladysparks.ttaenggrang.domain.tax.dto.TaxDTO;
import com.ladysparks.ttaenggrang.domain.tax.entity.Tax;
import com.ladysparks.ttaenggrang.domain.tax.mapper.TaxMapper;
import com.ladysparks.ttaenggrang.domain.tax.repository.TaxRepository;
import com.ladysparks.ttaenggrang.domain.teacher.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaxService {

    private final TaxRepository taxRepository;
    private final TaxMapper taxMapper;
    private final TeacherService teacherService;

    public TaxService(TaxRepository taxRepository,
                      TaxMapper taxMapper, TeacherService teacherService) {
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
        this.teacherService = teacherService;
    }

    // 특정 교사의 세금 목록 조회
    public List<TaxDTO> findTaxesByTeacher(Optional<Long> teacherId) {
        return taxRepository.findByTeacherId(teacherId.orElseGet(teacherService::getCurrentTeacherId)).stream()
                .map(taxMapper::toDto)
                .collect(Collectors.toList());
    }

    // 특정 세금 정보 조회
    public TaxDTO findTaxById(Long taxId) {
        Tax tax = taxRepository.findById(taxId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 세금 정보를 찾을 수 없습니다."));
        return taxMapper.toDto(tax);
    }

    // 세금 정보 등록
    @Transactional
    public TaxDTO addTax(TaxDTO taxDTO) {
        Long teacherId = teacherService.getCurrentTeacherId();
        taxDTO.setTeacherId(teacherId);
        Tax tax = taxMapper.toEntity(taxDTO);
        Tax savedTax = taxRepository.save(tax);
        return taxMapper.toDto(savedTax);
    }

    // 세금 정보 수정
    @Transactional
    public TaxDTO updateTax(Long taxId, TaxDTO taxDTO) {
        Tax tax = taxRepository.findById(taxId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 세금 정보를 찾을 수 없습니다."));

        tax.setTaxName(taxDTO.getTaxName());
        tax.setTaxRate(taxDTO.getTaxRate());
        tax.setTaxDescription(taxDTO.getTaxDescription());

        Tax updatedTax = taxRepository.save(tax);
        return taxMapper.toDto(updatedTax);
    }

    // 세금 정보 삭제
    @Transactional
    public void deleteTax(Long taxId) {
        taxRepository.deleteById(taxId);
    }

}