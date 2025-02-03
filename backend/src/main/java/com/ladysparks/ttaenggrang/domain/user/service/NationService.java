package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.domain.user.dto.NationCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Nation;
import com.ladysparks.ttaenggrang.domain.user.repository.NationRepository;
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

    public ApiResponse<?> createNation(NationCreateDTO nationCreateDTO) {
        // 1. 국가 이름 중복 체크
        Optional<Nation> existingNation = nationRepository.findByNationName(nationCreateDTO.getNationName());
        if (existingNation.isPresent()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "국가 정보 등록 요청이 유효하지 않습니다. ",
                    Map.of("error", List.of(new ErrorResponse.FieldErrorDetail("nationName", "국가 이름이 중복되었습니다.")))
            );
        }

        // 2. 국가 정보 저장
        Nation nation = Nation.builder()
                .nationName(nationCreateDTO.getNationName())
                .establishedDate(nationCreateDTO.getEstablishedDate())
                .population(nationCreateDTO.getPopulation())
                .currency(nationCreateDTO.getCurrency())
                .build();
        Nation savedNation = nationRepository.save(nation);

        // 3. 응답 데이터 생성
        Map<String, Object> responseData = Map.of(
                "nationId", savedNation.getId(),
                "nationName", savedNation.getNationName(),
                "establishedDate", savedNation.getEstablishedDate(),
                "population", savedNation.getPopulation(),
                "currency", savedNation.getCurrency()
        );

        return ApiResponse.created("국가 정보 등록이 완료되었습니다.", responseData);

    }
}
