package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.bank.SavingsProduct;
import com.ladysparks.ttaenggrang.dto.SavingsProductDTO;
import com.ladysparks.ttaenggrang.mapper.SavingsProductMapper;
import com.ladysparks.ttaenggrang.repository.SavingsProductRepository;
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
    public SavingsProductDTO addSavingsProducts(SavingsProductDTO savingsProductDTO) {
        SavingsProduct savingsProduct = savingsProductMapper.toEntity(savingsProductDTO);
        SavingsProduct savedSavingsProduct = savingsProductRepository.save(savingsProduct);
        return savingsProductMapper.toDto(savedSavingsProduct);
    }

    // 적금 상품 [조회]
    public List<SavingsProductDTO> findSavingsProducts(Long teacherId) {
        List<SavingsProduct> savingsProducts = savingsProductRepository.findByTeacherId(teacherId);
        return savingsProducts
                .stream()
                .map(savingsProductMapper::toDto)
                .collect(Collectors.toList());
    }

}
