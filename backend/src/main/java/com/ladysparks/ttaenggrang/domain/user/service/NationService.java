package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.domain.user.dto.NationCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Nation;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.user.repository.NationRepository;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.global.response.ErrorResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NationService {

    private final NationRepository nationRepository;
    private final TeacherRepository teacherRepository;

    public ApiResponse<NationCreateDTO> createNation(Long teacherId, NationCreateDTO nationCreateDTO) {
        // 1. 교사 엔티티 조회 (TeacherRepository 필요)
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isEmpty()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "해당 교사를 찾을 수 없습니다.", null);
        }
        Teacher teacher = teacherOptional.get();

        // 2. 교사가 이미 국가를 가지고 있는지 확인
        Optional<Nation> existingNation = nationRepository.findByTeacher_Id(teacherId);
        if (existingNation.isPresent()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "이미 국가가 등록되어 있습니다.", null);
        }

        // 3. 국가 정보 저장
        Nation nation = Nation.builder()
                .teacher(teacher)  // 교사 ID 저장
                .nationName(nationCreateDTO.getNationName())
                .establishedDate(nationCreateDTO.getEstablishedDate())
                .population(nationCreateDTO.getPopulation())
                .currency(nationCreateDTO.getCurrency())
                .savingsGoalAmount(
                        nationCreateDTO.getSavingsGoalAmount() != 0 ? nationCreateDTO.getSavingsGoalAmount() : 100000)
                .establishedDate(
                        nationCreateDTO.getEstablishedDate() != null
                                ? nationCreateDTO.getEstablishedDate()
                                : new Timestamp(System.currentTimeMillis())  // 현재 시각으로 기본값 설정
                )
                .build();
        Nation savedNation = nationRepository.save(nation);

        // 4. 응답 데이터 생성
        NationCreateDTO responseDTO = new NationCreateDTO(
                savedNation.getId(),
                savedNation.getNationName(),
                savedNation.getPopulation(),
                savedNation.getCurrency(),
                savedNation.getSavingsGoalAmount(),
                savedNation.getEstablishedDate()
        );

        return ApiResponse.created("국가 정보 등록이 완료되었습니다.", responseDTO);
    }

    // 국가 [조회]
    public ApiResponse<NationCreateDTO> getNationByTeacherId(Long teacherId) {
        Optional<Nation> optionalNation = nationRepository.findByTeacher_Id(teacherId);
        if (optionalNation.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "등록된 국가가 없습니다.", null);
        }

        Nation nation = optionalNation.get();
        NationCreateDTO responseDTO = new NationCreateDTO(
                nation.getId(),
                nation.getNationName(),
                nation.getPopulation(),
                nation.getCurrency(),
                nation.getSavingsGoalAmount(),
                nation.getEstablishedDate()
        );

        return ApiResponse.success("국가 정보 조회 성공", responseDTO);
    }

    @PersistenceContext
    private EntityManager entityManager;

    // 국가 [삭제]
    @Transactional
    public ApiResponse<Void> deleteNation(Long teacherId) {

        // 1. 삭제할 국가 존재하는지 확인
        Optional<Nation> nationOptional = nationRepository.findByTeacher_Id(teacherId);

        if (nationOptional.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "등록된 국가가 없습니다.", null);
        }

        // 2. 국가 삭제
        nationRepository.deleteByTeacher_Id(teacherId);

        // 3. 강제 DB 반영
        entityManager.flush();

        // 4. 성공 응답 반환
        return ApiResponse.success("국가 정보가 삭제되었습니다.", null);
    }
}
