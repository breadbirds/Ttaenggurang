package com.ladysparks.ttaenggrang.domain.bank.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.SavingsSubscriptionDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsSubscription;
import com.ladysparks.ttaenggrang.domain.bank.mapper.SavingsSubscriptionMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.SavingsSubscriptionRepository;
import com.ladysparks.ttaenggrang.domain.student.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavingsSubscriptionService {

    private final SavingsSubscriptionRepository savingsSubscriptionRepository;
    private final SavingsSubscriptionMapper savingsSubscriptionMapper;
    private final StudentService studentService;
    private final SavingsProductService savingsProductService;
    private final SavingsDepositService savingsDepositService;

    // 적금 가입 [등록]
    @Transactional
    public SavingsSubscriptionDTO addSavingsSubscription(SavingsSubscriptionDTO savingsSubscriptionDTO) {
        Long studentId = studentService.getCurrentStudentId();

        // 적금 가입 중복 확인
        boolean exists = savingsSubscriptionRepository.existsByStudentIdAndSavingsProductId(
                savingsSubscriptionDTO.getStudentId(), savingsSubscriptionDTO.getSavingsProductId());
        if (exists) {
            throw new IllegalArgumentException("이미 해당 적금 상품에 가입한 학생입니다.");
        }

        Long durationWeeks = savingsProductService.findDurationWeeksById(savingsSubscriptionDTO.getSavingsProductId());

        // startDate 자동 계산 (오늘 이후 가장 가까운 `depositDayOfWeek`)
        LocalDate today = LocalDate.now();
        LocalDate startLocalDate = today.with(TemporalAdjusters.next(savingsSubscriptionDTO.getDepositDayOfWeek()));

        // endDate 자동 계산 (startDate + durationWeeks 주 후)
        LocalDate endLocalDate = startLocalDate.plusWeeks(durationWeeks);

        // 납입 일정 생성
        List<LocalDate> depositDates = calculateDepositDates(startLocalDate, endLocalDate, savingsSubscriptionDTO.getDepositDayOfWeek());

        // DTO에 반영
        savingsSubscriptionDTO.setStudentId(studentId);
        savingsSubscriptionDTO.setStartDate(startLocalDate);
        savingsSubscriptionDTO.setEndDate(endLocalDate);

        // Entity 변환 및 저장
        SavingsSubscription savingsSubscription = savingsSubscriptionMapper.toEntity(savingsSubscriptionDTO);
        SavingsSubscription savedSavingsSubscription = savingsSubscriptionRepository.save(savingsSubscription);
        SavingsSubscriptionDTO savedSavingsSubscriptionDTO = savingsSubscriptionMapper.toDto(savedSavingsSubscription);
        savedSavingsSubscriptionDTO.setDepositSchedule(depositDates); // 납입 일정 추가

        // depositSchedule을 기반으로 SavingsDeposit 자동 생성
        savingsDepositService.addSavingsDeposits(savingsSubscription, depositDates);

        // 결과 DTO 반환
        return savedSavingsSubscriptionDTO;
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
    public List<SavingsSubscriptionDTO> findSavingsSubscriptions() {
        Long studentId = studentService.getCurrentStudentId();
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
