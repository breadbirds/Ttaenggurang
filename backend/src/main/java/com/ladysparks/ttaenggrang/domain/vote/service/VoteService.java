package com.ladysparks.ttaenggrang.domain.vote.service;

import com.ladysparks.ttaenggrang.domain.user.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.domain.vote.dto.VoteCreateDTO;
import com.ladysparks.ttaenggrang.domain.vote.entity.Vote;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteItem;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteMode;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteStatus;
import com.ladysparks.ttaenggrang.domain.vote.repository.VoteItemRepository;
import com.ladysparks.ttaenggrang.domain.vote.repository.VoteRepository;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final StudentRepository studentRepository;
    private final VoteItemRepository voteItemRepository;
    private final TeacherRepository teacherRepository;

    // ✅ 이메일로 교사 ID 조회 메서드
    public Long getTeacherIdByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 교사를 찾을 수 없습니다."))
                .getId();
    }

    // 투표 [생성]
    @Transactional
    public ApiResponse<VoteCreateDTO> createVote(VoteCreateDTO voteCreateDTO, Long teacherId) {

        // 1. 진행 중인 투표 확인
        Optional<Vote> inProgressVote = voteRepository.findByStatus(VoteStatus.IN_PROGRESS);
        if (inProgressVote.isPresent()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "진행 중인 투표가 있어 새 투표를 생성할 수 없습니다.", null);
        }

        // 2. 새 투표 생성
        Vote vote = Vote.builder()
                .title(voteCreateDTO.getTitle())
                .startDate(voteCreateDTO.getStartDate())
                .endDate(voteCreateDTO.getEndDate())
                .voteMode(voteCreateDTO.getVoteMode())
                .status(VoteStatus.IN_PROGRESS)
                .build();

        voteRepository.save(vote);

        // ✅ 3. 학생 리스트를 저장할 변수 선언 (초기값 null)
        List<StudentResponseDTO> studentResponseList = null;

        // 4. 투표 모드가 STUDENT인 경우, 학생 리스트를 투표 항목으로 추가
        if (voteCreateDTO.getVoteMode() == VoteMode.STUDENT) {
            List<Student> studentList = studentRepository.findAllByTeacherId(teacherId);
            if (studentList.isEmpty()) {
                return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "우리 반 학생이 없습니다.", null);
            }

            // 학생 리스트를 투표 항목으로 저장
            studentList.forEach(student -> {
                VoteItem voteItem = VoteItem.builder()
                        .vote(vote)
                        .student(student)
                        .voteCount(0)  // 초기 투표 수는 0
                        .build();
                voteItemRepository.save(voteItem);
            });

            // ✅ 학생 리스트를 DTO로 변환하고 변수에 저장
            studentResponseList = studentList.stream()
                    .map(student -> new StudentResponseDTO(
                            student.getId(),
                            student.getUsername(),
                            student.getName(),
                            student.getProfileImage(),
                            student.getTeacher(),
                            student.getBankAccount(),
                            null  // 토큰 값은 필요 시 추가
                    ))
                    .collect(Collectors.toList());
        }

        // 5. 투표 정보 + 학생 리스트를 DTO로 변환해서 반환
        VoteCreateDTO response = new VoteCreateDTO();
        response.setTitle(vote.getTitle());
        response.setStartDate(vote.getStartDate());
        response.setEndDate(vote.getEndDate());
        response.setVoteMode(vote.getVoteMode());
        response.setVoteStatus(vote.getStatus());
        response.setStudents(studentResponseList);  // ✅ 학생 리스트 포함 (null일 수도 있음)

        return ApiResponse.success("새 투표가 성공적으로 생성되었습니다!", response);
    }

    // ✅ 진행 중인 투표 조회 메서드
    @Transactional(readOnly = true)
    public ApiResponse<VoteCreateDTO> getCurrentVote() {
        Optional<Vote> currentVote = voteRepository.findByStatus(VoteStatus.IN_PROGRESS);

        if (currentVote.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "진행 중인 투표가 없습니다.", null);
        }

        Vote vote = currentVote.get();

        // 투표 정보를 DTO로 변환
        VoteCreateDTO response = new VoteCreateDTO();
        response.setId(vote.getId());
        response.setTitle(vote.getTitle());
        response.setStartDate(vote.getStartDate());
        response.setEndDate(vote.getEndDate());
        response.setVoteMode(vote.getVoteMode());
        response.setVoteStatus(vote.getStatus());

        return ApiResponse.success("진행 중인 투표 정보 조회 성공", response);
    }

    // ✅ 진행 중인 투표 종료 메서드
    @Transactional
    public ApiResponse<String> stopCurrentVote() {
        Optional<Vote> currentVote = voteRepository.findByStatus(VoteStatus.IN_PROGRESS);

        if (currentVote.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "진행 중인 투표가 없습니다.", null);
        }

        Vote vote = currentVote.get();
        vote.setStatus(VoteStatus.COMPLETED);  // ✅ 상태를 COMPLETED로 변경
        voteRepository.save(vote);

        return ApiResponse.success("진행 중인 투표가 성공적으로 종료되었습니다.");
    }

}
