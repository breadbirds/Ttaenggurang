package com.ladysparks.ttaenggrang.domain.teacher.service;

import com.ladysparks.ttaenggrang.domain.teacher.dto.NationDTO;
import com.ladysparks.ttaenggrang.domain.teacher.entity.Nation;
import com.ladysparks.ttaenggrang.domain.teacher.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.teacher.mapper.NationMapper;
import com.ladysparks.ttaenggrang.domain.teacher.repository.NationRepository;
import com.ladysparks.ttaenggrang.domain.teacher.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NationService {

    private final NationMapper nationMapper;
    private final NationRepository nationRepository;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;

    public ApiResponse<NationDTO> createNation(Long teacherId, NationDTO nationDTO) {
        // 1. 교사 엔티티 조회
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교사를 찾을 수 없습니다."));

        // 2. 교사가 이미 국가를 가지고 있는지 확인
        if (teacher.getNation() != null) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "이미 국가가 등록되어 있습니다.", null);
        }

        // 3. 국가 정보 저장
        Nation nation = Nation.builder()
                .nationName(nationDTO.getNationName())
                .population(nationDTO.getPopulation())
                .currency(nationDTO.getCurrency())
                .savingsGoalAmount(
                        nationDTO.getSavingsGoalAmount() != 0 ? nationDTO.getSavingsGoalAmount() : 100000)
                .establishedDate(
                        nationDTO.getEstablishedDate() != null
                                ? nationDTO.getEstablishedDate()
                                : new Timestamp(System.currentTimeMillis())  // 현재 시각으로 기본값 설정
                )
                .nationalTreasury(0)
                .build();
        Nation savedNation = nationRepository.save(nation);

        // 4. 교사 엔터티에 국가 설정 후 저장
        teacher.setNation(savedNation);
        teacherRepository.save(teacher);

        // 4. 응답 데이터 생성
        NationDTO responseDTO = new NationDTO(
                savedNation.getId(),
                savedNation.getNationName(),
                savedNation.getPopulation(),
                savedNation.getCurrency(),
                savedNation.getSavingsGoalAmount(),
                savedNation.getNationalTreasury(),
                savedNation.getEstablishedDate(),
                savedNation.getProfileImageUrl()
        );

        return ApiResponse.created("국가 정보 등록이 완료되었습니다.", responseDTO);
    }

    // 국가 [조회]
    public ApiResponse<NationDTO> getNationByTeacherId(Long teacherId) {
        // 1. 교사 엔티티 조회
        Long nationId = teacherService.findById(teacherId);

        // 2. 교사가 이미 국가를 가지고 있는지 확인
        Optional<Nation> optionalNation = nationRepository.findById(nationId);
        if (optionalNation.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "등록된 국가가 없습니다.", null);
        }

        Nation nation = optionalNation.get();
        NationDTO responseDTO = new NationDTO(
                nation.getId(),
                nation.getNationName(),
                nation.getPopulation(),
                nation.getCurrency(),
                nation.getSavingsGoalAmount(),
                nation.getNationalTreasury(),
                nation.getEstablishedDate(),
                nation.getProfileImageUrl()
        );

        return ApiResponse.success("국가 정보 조회 성공", responseDTO);
    }

    @PersistenceContext
    private EntityManager entityManager;

    // 국가 [삭제]
    @Transactional
    public ApiResponse<Void> deleteNation(Long teacherId) {

        // 1. 교사 엔티티 조회
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교사를 찾을 수 없습닌다."));

        // 2. 교사가 이미 국가를 가지고 있는지 확인
        Nation nation = teacher.getNation();
        if (nation == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "등록된 국가가 없습니다.", null);
        }

        // 3. 국가 삭제
        nationRepository.delete(nation);

        // 4. 교사 엔티티에서 국가 참조 제거 (양방향 관계일 경우)
        teacher.setNation(null);
        teacherRepository.save(teacher);

        // 5. 강제 DB 반영
        entityManager.flush();

        // 6. 성공 응답 반환
        return ApiResponse.success("국가 정보가 삭제되었습니다.", null);
    }

    @Transactional
    public NationDTO updateNationFunding(Long nationId, int funding) {
        // Nation 조회
        Nation nation = nationRepository.findById(nationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 국가를 찾을 수 없습니다."));

        // funding 값 업데이트
        nation.setNationalTreasury(nation.getNationalTreasury() + funding);

        Nation updatedNation = nationRepository.save(nation);

        // NationCreateDTO로 변환 후 반환
        return nationMapper.toDto(updatedNation);
    }

    public NationDTO findNationByTeacherId(Long teacherId) {
        // 1. 교사 엔티티 조회
        Long nationId = teacherService.findById(teacherId);

        // 2. 교사가 이미 국가를 가지고 있는지 확인
        Optional<Nation> optionalNation = nationRepository.findById(nationId);
        if (optionalNation.isEmpty()) {
            throw new NotFoundException("등록된 국가가 없습니다.");
        }

        Nation nation = optionalNation.get();

        return nationMapper.toDto(nation);
    }


}
