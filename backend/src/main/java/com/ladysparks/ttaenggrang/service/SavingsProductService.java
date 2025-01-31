package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.bank.SavingsProduct;
import com.ladysparks.ttaenggrang.dto.SavingsProductDTO;
import com.ladysparks.ttaenggrang.mapper.SavingsProductMapper;
import com.ladysparks.ttaenggrang.repository.SavingsProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SavingsProductService {

    private final SavingsProductRepository savingsProductRepository;
    private final SavingsProductMapper savingsProductMapper;

    @Autowired
    public SavingsProductService(SavingsProductRepository savingsProductRepository, SavingsProductMapper savingsProductMapper) {
        this.savingsProductRepository = savingsProductRepository;
        this.savingsProductMapper = savingsProductMapper;
    }

    // 적금 상품 [등록]
    @Transactional
    public SavingsProductDTO addSavingsProducts(SavingsProductDTO savingsProductDTO) {
        if (savingsProductDTO == null) {
            throw new IllegalArgumentException("요청된 적금 상품 정보가 유효하지 않습니다.");
        }

        // 1. 필수 값 검증
        if (savingsProductDTO.getTeacherId() == null || savingsProductDTO.getName() == null) {
            throw new IllegalArgumentException("teacherId와 name은 필수 입력 값입니다.");
        }

        // 2. 중복 적금 상품 방지 (같은 교사가 같은 이름의 적금 상품을 등록할 수 없음)
        boolean exists = savingsProductRepository.existsByTeacherIdAndName(
                savingsProductDTO.getTeacherId(), savingsProductDTO.getName());

        if (exists) {
            throw new IllegalArgumentException("해당 적금 상품이 이미 존재합니다. (교사 ID: " + savingsProductDTO.getTeacherId() + ", 상품명: " + savingsProductDTO.getName() + ")");
        }

        // 3. Entity 변환 및 저장
        SavingsProduct savingsProduct = savingsProductMapper.toEntity(savingsProductDTO);
        SavingsProduct savedSavingsProduct = savingsProductRepository.save(savingsProduct);

        return savingsProductMapper.toDto(savedSavingsProduct);
    }

    // 적금 상품 [조회]
    public List<SavingsProductDTO> findSavingsProducts(Long teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException("teacherId는 필수 입력 값입니다.");
        }

        List<SavingsProduct> savingsProducts = savingsProductRepository.findByTeacherId(teacherId);

        if (savingsProducts.isEmpty()) {
            throw new EntityNotFoundException("해당 교사의 적금 상품이 존재하지 않습니다. ID: " + teacherId);
        }

        return savingsProducts.stream()
                .map(savingsProductMapper::toDto)
                .collect(Collectors.toList());
    }

}
