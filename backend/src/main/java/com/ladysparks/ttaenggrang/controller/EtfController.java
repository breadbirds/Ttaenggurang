package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.dto.etf.EtfDTO;
import com.ladysparks.ttaenggrang.dto.stock.StockDTO;
import com.ladysparks.ttaenggrang.service.EtfService;
import com.ladysparks.ttaenggrang.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/etfs")
public class EtfController {
    private final EtfService etfService; // StockService 주입

    // 주식 목록 전체 조회
    @GetMapping
    public ResponseEntity<List<EtfDTO>> getStocks() {
        List<EtfDTO> result = etfService.findEtfs(); // 모든 주식 정보를 반환
        return ResponseEntity.ok(result); // HTTP 200 OK와 함께 결과 반환
    }

    // 주식 상세 조회
    @GetMapping("/{etfId}")
    public ResponseEntity<EtfDTO> getEtf(@PathVariable("etfId") int etfId) {
        Optional<EtfDTO> result = etfService.findEtf(etfId);

        // 값이 없으면 404 Not Found 반환
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get()); // 값이 있으면 200 OK와 함께 결과 반환
        } else {
            return ResponseEntity.notFound().build(); // 값이 없으면 404 Not Found 반환
        }
    }
}
