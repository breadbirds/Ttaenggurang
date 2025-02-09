package com.ladysparks.ttaenggrang.domain.nation.service;

import com.ladysparks.ttaenggrang.domain.nation.dto.NationDTO;
import com.ladysparks.ttaenggrang.domain.nation.entity.Nation;
import com.ladysparks.ttaenggrang.domain.nation.mapper.NationMapper;
import com.ladysparks.ttaenggrang.domain.user.dto.TeacherResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.nation.repository.NationRepository;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.domain.user.service.StudentService;
import com.ladysparks.ttaenggrang.domain.user.service.TeacherService;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NationService {

    private final NationMapper nationMapper;
    private final NationRepository nationRepository;
    private final TeacherService teacherService;

    public ApiResponse<NationDTO> createNation(Long teacherId, NationDTO nationDTO) {
        // 1. 교사 엔티티 조회
        Long nationId = teacherService.findById(teacherId);

        // 2. 교사가 이미 국가를 가지고 있는지 확인
        if (nationId != -1) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "이미 국가가 등록되어 있습니다.", null);
        }

        // 3. 국가 정보 저장
        Nation nation = Nation.builder()
                .nationName(nationDTO.getNationName())
                .establishedDate(nationDTO.getEstablishedDate())
                .population(nationDTO.getPopulation())
                .currency(nationDTO.getCurrency())
                .savingsGoalAmount(
                        nationDTO.getSavingsGoalAmount() != 0 ? nationDTO.getSavingsGoalAmount() : 100000)
                .establishedDate(
                        nationDTO.getEstablishedDate() != null
                                ? nationDTO.getEstablishedDate()
                                : new Timestamp(System.currentTimeMillis())  // 현재 시각으로 기본값 설정
                )
                .build();
        Nation savedNation = nationRepository.save(nation);

        // 4. 응답 데이터 생성
        NationDTO responseDTO = new NationDTO(
                savedNation.getId(),
                savedNation.getNationName(),
                savedNation.getPopulation(),
                savedNation.getCurrency(),
                savedNation.getSavingsGoalAmount(),
                savedNation.getFunding(),
                savedNation.getEstablishedDate()
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
                nation.getFunding(),
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
        // 1. 교사 엔티티 조회
        Long nationId = teacherService.findById(teacherId);

        // 2. 교사가 이미 국가를 가지고 있는지 확인
        if (nationId == -1) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "등록된 국가가 없습니다.", null);
        }

        // 2. 국가 삭제
        nationRepository.deleteById(nationId);

        // 3. 강제 DB 반영
        entityManager.flush();

        // 4. 성공 응답 반환
        return ApiResponse.success("국가 정보가 삭제되었습니다.", null);
    }

    @Transactional
    public NationDTO updateNationFunding(Long nationId, int funding) {
        // Nation 조회
        Nation nation = nationRepository.findById(nationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 국가를 찾을 수 없습니다."));

        // funding 값 업데이트
        nation.setFunding(nation.getFunding() + funding);

        Nation updatedNation = nationRepository.save(nation);

        // NationCreateDTO로 변환 후 반환
        return nationMapper.toDto(updatedNation);
    }

}
