package com.ladysparks.ttaenggrang.domain.vote.service;

import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankAccountRepository;
import com.ladysparks.ttaenggrang.domain.student.entity.Student;
import com.ladysparks.ttaenggrang.domain.student.repository.StudentRepository;
import com.ladysparks.ttaenggrang.domain.vote.entity.Vote;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteItem;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteStatus;
import com.ladysparks.ttaenggrang.domain.vote.repository.VoteItemRepository;
import com.ladysparks.ttaenggrang.domain.vote.repository.VoteRepository;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final VoteRepository voteRepository;
    private final VoteItemRepository voteItemRepository;
    private final BankAccountRepository bankAccountRepository;
    private final StudentRepository studentRepository;

    // ✅ 최종 주급 지급 방법 (투표 여부에 따라 분기 처리)
    @Transactional
    public ApiResponse<String> distributeSalaries() {

        // 1. 진행 중인 투표가 있는지 확인
        Optional<Vote> inProgressVote = voteRepository.findByStatus(VoteStatus.IN_PROGRESS);

        if (inProgressVote.isPresent()) {
            // 1. 투표가 진행된 경우: 인센티브 포함 주급 지급
            return updateSalariesBasedOnVote(inProgressVote.get().getId());
        } else {
            // 2. 투표가 없는 경우: 기본 주급 지급
            return distributeBaseSalaries();
        }
    }

    // ✅ [ 투표 결과 기반 ] 주급 지급
    @Transactional
    public ApiResponse<String> updateSalariesBasedOnVote(Long voteId) {
        Optional<Vote> voteOptional = voteRepository.findById(voteId);
        if (voteOptional.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "해당 투표를 찾을 수 없습니다.", null);
        }

        Vote vote = voteOptional.get();

        // 투표 결과 (투표 수 기준 내림차순)
        List<VoteItem> voteItems = voteItemRepository.findByVoteOrderByVoteCountDesc(vote);
        if (voteItems.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "투표 결과가 없습니다.", null);
        }

        // 1등, 2등, 3등 학생
        VoteItem firstPlace = voteItems.size() > 0 ? voteItems.get(0) : null;
        VoteItem secondPlace = voteItems.size() > 1 ? voteItems.get(1) : null;
        VoteItem thirdPlace = voteItems.size() > 2 ? voteItems.get(2) : null;

        // 인센티브 적용 및 주급 업데이트
        applyIncentiveAndUpdateSalary(firstPlace, 0.5);  // 1등: 50%
        applyIncentiveAndUpdateSalary(secondPlace, 0.3); // 2등: 30%
        applyIncentiveAndUpdateSalary(thirdPlace, 0.2);  // 3등: 20%

        return ApiResponse.success("투표 결과를 기반으로 주급이 성공적으로 업데이트되었습니다.");
    }

    // ✅ 인센티브 적용 및 주급 업데이트 메서드
    private void applyIncentiveAndUpdateSalary(VoteItem voteItem, double incentiveRate) {
        if (voteItem == null) return;  // 해당 순위에 학생이 없으면 패스

        Student student = voteItem.getStudent();
        BankAccount bankAccount = student.getBankAccount();

        int baseSalary = student.getJob().getBaseSalary();  // 기본급
        int totalSalary = baseSalary + (int)(baseSalary * incentiveRate);  // 인센티브 포함 주급

        bankAccount.setBalance(bankAccount.getBalance() + totalSalary);  // 잔액 업데이트
        bankAccountRepository.save(bankAccount);
    }

    // ✅ [ 기본 ] 주급 지급 (투표가 없을 경우)
    @Transactional
    public ApiResponse<String> distributeBaseSalaries() {
        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "학생 정보가 없습니다.", null);
        }

        // 모든 학생에게 기본 주급 지급
        students.forEach(student -> {
            int baseSalary = student.getJob().getBaseSalary();
            BankAccount bankAccount = student.getBankAccount();

            bankAccount.setBalance(bankAccount.getBalance() + baseSalary);
            bankAccountRepository.save(bankAccount);
        });

        return ApiResponse.success("투표가 진행되지 않아 기본 주급이 지급되었습니다.");
    }
}
