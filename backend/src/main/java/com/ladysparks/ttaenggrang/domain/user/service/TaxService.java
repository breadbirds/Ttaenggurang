package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.domain.user.dto.TaxCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Tax;
import com.ladysparks.ttaenggrang.domain.user.repository.TaxRepository;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;


    // 세금 [생성]
    public ApiResponse<TaxCreateDTO> createTax(TaxCreateDTO taxCreateDTO) {

        // 1. 세금 이름 중복 확인
        Optional<Tax> existingTax = taxRepository.findByTaxName(taxCreateDTO.getTaxName());
        if (existingTax.isPresent()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "세금 이름이 이미 존재합니다.", null);
        }

        // 2. 세금 저장
        Tax tax = Tax.builder()
                .taxName(taxCreateDTO.getTaxName())
                .taxRate(taxCreateDTO.getTaxRate())
                .taxDescription(taxCreateDTO.getTaxDescription())
                .build();

        Tax savedTax = taxRepository.save(tax);

        // 3. 응답 반환
        TaxCreateDTO responseDTO = new TaxCreateDTO(
                savedTax.getId(),
                savedTax.getTaxName(),
                savedTax.getTaxRate(),
                savedTax.getTaxDescription()
        );

        return ApiResponse.created("세금 등록이 완료되었습니다.", responseDTO);
    }

    // 세금 [전체 조회]
    public ApiResponse<List<TaxCreateDTO>> getAllTaxes() {
        List<Tax> taxes = taxRepository.findAll();

        // Tax Entity -> TaxCreateDTO 변환
        List<TaxCreateDTO> taxDTOList = taxes.stream()
                .map(tax -> new TaxCreateDTO(
                        tax.getId(),
                        tax.getTaxName(),
                        tax.getTaxRate(),
                        tax.getTaxDescription()
                ))
                .collect(Collectors.toList());

        return ApiResponse.success("전체 세금 조회 성공", taxDTOList);
    }
}
