package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.bank.SavingsSubscription;
import com.ladysparks.ttaenggrang.dto.SavingsSubscriptionDTO;
import com.ladysparks.ttaenggrang.mapper.SavingsSubscriptionMapper;
import com.ladysparks.ttaenggrang.repository.SavingsSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public SavingsSubscriptionDTO addSavingsSubscription(SavingsSubscriptionDTO savingsSubscriptionDTO) {
        SavingsSubscription savingsSubscription = savingsSubscriptionMapper.toEntity(savingsSubscriptionDTO);
        SavingsSubscription savedSavingsSubscription = savingsSubscriptionRepository.save(savingsSubscription);
        return savingsSubscriptionMapper.toDto(savedSavingsSubscription);
    }

    // 적금 가입 내역 [전체 조회]
    public List<SavingsSubscriptionDTO> findSavingsSubscriptions(Long studentId) {
        List<SavingsSubscription> savingsSubscriptions = savingsSubscriptionRepository.findByStudentId(studentId);
        return savingsSubscriptions
                .stream()
                .map(savingsSubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    // 적금 가입 학생 내역 [전체 조회]
    public List<SavingsSubscriptionDTO> findSavingsSubscriptionsBySavingProductId(Long savingsProductId) {
        List<SavingsSubscription> savingsSubscriptions = savingsSubscriptionRepository.findBySavingsProductId(savingsProductId);
        return savingsSubscriptions
                .stream()
                .map(savingsSubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

}
