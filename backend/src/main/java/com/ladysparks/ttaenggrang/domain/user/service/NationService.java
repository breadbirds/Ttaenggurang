package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.domain.user.dto.NationCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Nation;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.user.repository.NationRepository;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        Optional<Nation> existingNation = nationRepository.findByTeacherId(teacherId);
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
                .build();
        Nation savedNation = nationRepository.save(nation);

        // 4. 응답 데이터 생성
        NationCreateDTO responseDTO = new NationCreateDTO(
                savedNation.getId(),
                savedNation.getNationName(),
                savedNation.getPopulation(),
                savedNation.getCurrency(),
                savedNation.getEstablishedDate()
        );

        return ApiResponse.created("국가 정보 등록이 완료되었습니다.", responseDTO);
    }

    // 국가 [조회]
    public ApiResponse<NationCreateDTO> getNationByTeacherId(Long teacherId) {
        Optional<Nation> optionalNation = nationRepository.findByTeacherId(teacherId);
        if (optionalNation.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "등록된 국가가 없습니다.", null);
        }

        Nation nation = optionalNation.get();
        NationCreateDTO responseDTO = new NationCreateDTO(
                nation.getId(),
                nation.getNationName(),
                nation.getPopulation(),
                nation.getCurrency(),
                nation.getEstablishedDate()
        );

        return ApiResponse.success("국가 정보 조회 성공", responseDTO);
    }
}
