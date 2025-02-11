package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsProductDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsProduct;
import com.ladysparks.ttaenggrang.domain.bank.mapper.SavingsProductMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.SavingsProductRepository;
import com.ladysparks.ttaenggrang.domain.teacher.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingsProductService {

    private final SavingsProductRepository savingsProductRepository;
    private final SavingsProductMapper savingsProductMapper;
    private final TeacherService teacherService;

    // 적금 상품 [등록]
    @Transactional
    public SavingsProductDTO addSavingsProducts(SavingsProductDTO savingsProductDTO) {
        if (savingsProductDTO == null) {
            throw new IllegalArgumentException("요청된 적금 상품 정보가 유효하지 않습니다.");
        }

        Long teacherId = teacherService.getCurrentTeacherId();

        // 중복 적금 상품 방지 (같은 교사가 같은 이름의 적금 상품을 등록할 수 없음)
        boolean exists = savingsProductRepository.existsByTeacherIdAndName(
                teacherId, savingsProductDTO.getName());

        if (exists) {
            throw new IllegalArgumentException("해당 적금 상품이 이미 존재합니다. (교사 ID: " + teacherId + ", 상품명: " + savingsProductDTO.getName() + ")");
        }

        savingsProductDTO.setTeacherId(teacherId);
        SavingsProduct savingsProduct = savingsProductMapper.toEntity(savingsProductDTO);
        SavingsProduct savedSavingsProduct = savingsProductRepository.save(savingsProduct);

        return savingsProductMapper.toDto(savedSavingsProduct);
    }

    // 적금 상품 [조회]
    public List<SavingsProductDTO> findSavingsProducts() {
        Long teacherId = teacherService.getCurrentTeacherId();

        List<SavingsProduct> savingsProducts = savingsProductRepository.findByTeacherId(teacherId);

        if (savingsProducts.isEmpty()) {
            throw new EntityNotFoundException("해당 교사의 적금 상품이 존재하지 않습니다. ID: " + teacherId);
        }

        return savingsProducts.stream()
                .map(savingsProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public Long findDurationWeeksById(Long savingsProductId) {
        return savingsProductRepository.findDurationWeeksById(savingsProductId)
                .orElseThrow(() -> new IllegalIdentifierException("해당 적금 상품이 존재하지 않습니다. ID: " + savingsProductId));
    }

}
