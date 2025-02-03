package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsSubscription;
import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsSubscriptionDTO;
import com.ladysparks.ttaenggrang.domain.bank.mapper.SavingsSubscriptionMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.SavingsSubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsSubscriptionService {

    private final SavingsSubscriptionRepository savingsSubscriptionRepository;
    private final SavingsSubscriptionMapper savingsSubscriptionMapper;

    @Autowired
    public SavingsSubscriptionService(SavingsSubscriptionRepository savingsSubscriptionRepository, SavingsSubscriptionMapper savingsSubscriptionMapper) {
        this.savingsSubscriptionRepository = savingsSubscriptionRepository;
        this.savingsSubscriptionMapper = savingsSubscriptionMapper;
    }

    // 적금 가입 [등록]
    @Transactional
    public SavingsSubscriptionDTO addSavingsSubscription(SavingsSubscriptionDTO savingsSubscriptionDTO) {
        if (savingsSubscriptionDTO == null) {
            throw new IllegalArgumentException("요청된 적금 가입 정보가 유효하지 않습니다.");
        }

        // 1. 필수 값 확인 (studentId & savingsProductId)
        if (savingsSubscriptionDTO.getStudentId() == null || savingsSubscriptionDTO.getSavingsProductId() == null) {
            throw new IllegalArgumentException("studentId와 savingsProductId는 필수 입력 값입니다.");
        }

        // 2. 해당 학생이 이미 같은 적금 상품에 가입했는지 확인 (중복 가입 방지)
        boolean exists = savingsSubscriptionRepository.existsByStudentIdAndSavingsProductId(
                savingsSubscriptionDTO.getStudentId(), savingsSubscriptionDTO.getSavingsProductId());

        if (exists) {
            throw new IllegalArgumentException("이미 해당 적금 상품에 가입한 학생입니다.");
        }

        // 3. Entity 변환 및 저장
        SavingsSubscription savingsSubscription = savingsSubscriptionMapper.toEntity(savingsSubscriptionDTO);
        SavingsSubscription savedSavingsSubscription = savingsSubscriptionRepository.save(savingsSubscription);

        return savingsSubscriptionMapper.toDto(savedSavingsSubscription);
    }

    // 적금 가입 내역 [전체 조회]
    public List<SavingsSubscriptionDTO> findSavingsSubscriptions(Long studentId) {
        List<SavingsSubscription> savingsSubscriptions = savingsSubscriptionRepository.findByStudentId(studentId);

        if (savingsSubscriptions.isEmpty()) {
            throw new EntityNotFoundException("해당 학생의 적금 가입 내역이 존재하지 않습니다. ID: " + studentId);
        }

        return savingsSubscriptions.stream()
                .map(savingsSubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    // 적금 가입 학생 내역 [전체 조회]
    public List<SavingsSubscriptionDTO> findSavingsSubscriptionsBySavingProductId(Long savingsProductId) {
        List<SavingsSubscription> savingsSubscriptions = savingsSubscriptionRepository.findBySavingsProductId(savingsProductId);

        if (savingsSubscriptions.isEmpty()) {
            throw new EntityNotFoundException("해당 적금 상품의 가입 내역이 존재하지 않습니다. ID: " + savingsProductId);
        }

        return savingsSubscriptions.stream()
                .map(savingsSubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

}
