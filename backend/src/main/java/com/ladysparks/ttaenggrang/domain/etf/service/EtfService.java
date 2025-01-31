package com.ladysparks.ttaenggrang.domain.etf.service;

import com.ladysparks.ttaenggrang.domain.etf.entity.Etf;
import com.ladysparks.ttaenggrang.domain.etf.dto.EtfDTO;
import com.ladysparks.ttaenggrang.domain.etf.repository.EtfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class EtfService {
    private final EtfRepository etfRepository; //의존성 주입

    //목록 조회
    public Long save(EtfDTO etfDto) {
        // EtfDTO를 Etf 엔티티로 변환
        Etf etf = EtfDTO.toEntity(etfDto);
        // 변환된 엔티티를 DB에 저장
        etfRepository.save(etf);
        // 저장된 엔티티의 ID 반환
        return etf.getId();
    }

    public List<EtfDTO> findEtfs() {
        //모든 주식 데이터 조회
        List<Etf> etfs = etfRepository.findAll();
        // 조회된 ETF 엔티티 리스트를 ETFDTO 리스트로 변환
        return etfs.stream()
                .map(EtfDTO::fromEntity) // 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // 변환된 DTO를 리스트로 반환
    }
    public Optional<EtfDTO> findEtf(int etfId) {
        // ID로 주식 조회 후, ETFDTO로 변환하여 반환
        return etfRepository.findById(etfId)
                .map(EtfDTO::fromEntity); // 엔티티를 DTO로 변환
    }
}
