package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsDepositDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsDeposit;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsSubscription;
import com.ladysparks.ttaenggrang.domain.bank.mapper.SavingsDepositMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.SavingsDepositRepository;
import com.ladysparks.ttaenggrang.domain.bank.repository.SavingsSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingsDepositService {

    private final SavingsDepositRepository savingsDepositRepository;
    private final SavingsSubscriptionRepository savingsSubscriptionRepository;
    private final SavingsDepositMapper savingsDepositMapper;

    // 적금 납입 내역 조회 (특정 적금 가입)
    public List<SavingsDepositDTO> findSavingsDeposits(Long savingsSubscriptionId) {
        List<SavingsDeposit> deposits = savingsDepositRepository.findBySavingsSubscriptionId(savingsSubscriptionId);
        return deposits.stream()
                .map(savingsDepositMapper::toDto)
                .collect(Collectors.toList());
    }

    // 적금 납입 내역 추가
    @Transactional
    public SavingsDepositDTO addSavingsDeposit(SavingsDepositDTO savingsDepositDTO) {
        // 적금 가입 내역이 존재하는지 확인
        SavingsSubscription savingsSubscription = savingsSubscriptionRepository.findById(savingsDepositDTO.getSavingsSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("적금 가입 정보를 찾을 수 없습니다."));

        // 적금 납입 내역 저장
        SavingsDeposit savingsDeposit = savingsDepositMapper.toEntity(savingsDepositDTO);
        SavingsDeposit savedDeposit = savingsDepositRepository.save(savingsDeposit);

        return savingsDepositMapper.toDto(savedDeposit);
    }

}
