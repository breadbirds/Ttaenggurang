package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.etf.dto.EtfDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Tag(name = "Etf", description = "ETF API")
public interface EtfApiSpecification {
    @Operation(summary = "ETF 전체 조회", description = "💡 전체 ETF을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<EtfDTO>> getEtfs();
}
