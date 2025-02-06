package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsSubscriptionDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsSubscription;
import com.ladysparks.ttaenggrang.domain.bank.mapper.SavingsSubscriptionMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.SavingsSubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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

        // 필수 값 검증
        if (savingsSubscriptionDTO.getStudentId() == null || savingsSubscriptionDTO.getSavingsProductId() == null) {
            throw new IllegalArgumentException("studentId와 savingsProductId는 필수 입력 값입니다.");
        }

        if (savingsSubscriptionDTO.getDurationWeeks() <= 0) {
            throw new IllegalArgumentException("durationWeeks는 1주 이상이어야 합니다.");
        }

        if (savingsSubscriptionDTO.getDepositDayOfWeek() == null) {
            throw new IllegalArgumentException("depositDayOfWeek(납입 요일)은 필수 입력 값입니다.");
        }

        // 적금 가입 중복 확인
        boolean exists = savingsSubscriptionRepository.existsByStudentIdAndSavingsProductId(
                savingsSubscriptionDTO.getStudentId(), savingsSubscriptionDTO.getSavingsProductId());
        if (exists) {
            throw new IllegalArgumentException("이미 해당 적금 상품에 가입한 학생입니다.");
        }

        // startDate 자동 계산 (오늘 이후 가장 가까운 `depositDayOfWeek`)
        LocalDate today = LocalDate.now();
        LocalDate startLocalDate = today.with(TemporalAdjusters.nextOrSame(savingsSubscriptionDTO.getDepositDayOfWeek()));

        // endDate 자동 계산 (startDate + durationWeeks 주 후)
        LocalDate endLocalDate = startLocalDate.plusWeeks(savingsSubscriptionDTO.getDurationWeeks());

        // 납입 일정 생성
        List<LocalDate> depositDates = calculateDepositDates(startLocalDate, endLocalDate, savingsSubscriptionDTO.getDepositDayOfWeek());

        // DTO에 반영
        savingsSubscriptionDTO.setStartDate(Date.valueOf(startLocalDate));
        savingsSubscriptionDTO.setEndDate(Date.valueOf(endLocalDate));

        // Entity 변환 및 저장
        SavingsSubscription savingsSubscription = savingsSubscriptionMapper.toEntity(savingsSubscriptionDTO);
        SavingsSubscription savedSavingsSubscription = savingsSubscriptionRepository.save(savingsSubscription);

        // 결과 DTO 반환
        SavingsSubscriptionDTO responseDTO = savingsSubscriptionMapper.toDto(savedSavingsSubscription);
        responseDTO.setDepositSchedule(depositDates); // 납입 일정 추가

        return responseDTO;
    }

    // startDate부터 endDate까지 매주 depositDayOfWeek에 해당하는 날짜 리스트를 반환
    private List<LocalDate> calculateDepositDates(LocalDate startDate, LocalDate endDate, DayOfWeek depositDayOfWeek) {
        List<LocalDate> depositDates = new ArrayList<>();

        // 첫 납입일 계산 (startDate 이후 가장 가까운 depositDayOfWeek)
        LocalDate firstDepositDate = startDate.with(TemporalAdjusters.nextOrSame(depositDayOfWeek));

        // 지정된 기간 동안 매주 depositDayOfWeek에 맞춰 날짜 추가
        LocalDate currentDepositDate = firstDepositDate;
        while (currentDepositDate.isBefore(endDate)) {
            depositDates.add(currentDepositDate);
            currentDepositDate = currentDepositDate.plusWeeks(1); // 다음 주 동일한 요일
        }

        return depositDates;
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
