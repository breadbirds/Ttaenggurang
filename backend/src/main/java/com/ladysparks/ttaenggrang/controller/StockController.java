package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.StockApiSpecification;
import com.ladysparks.ttaenggrang.dto.stock.StockDTO;
import com.ladysparks.ttaenggrang.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController implements StockApiSpecification {
    private final StockService stockService; // StockService 주입

    // 주식 목록 전체 조회
    @GetMapping
    public ResponseEntity<List<StockDTO>> getStocks() {
        List<StockDTO> result = stockService.findStocks(); // 모든 주식 정보를 반환
        return ResponseEntity.ok(result); // HTTP 200 OK와 함께 결과 반환
    }

    // 주식 상세 조회
    @GetMapping("/{stockId}")
    public ResponseEntity<StockDTO> getStock(@PathVariable("stockId") int stockId) {
        Optional<StockDTO> result = stockService.findStock(stockId);

        // 값이 없으면 404 Not Found 반환
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get()); // 값이 있으면 200 OK와 함께 결과 반환
        } else {
            return ResponseEntity.notFound().build(); // 값이 없으면 404 Not Found 반환
        }
    }

    //주식 매수
    @PostMapping("/{stockId}/buy")
    public ResponseEntity<String> buyStock(@PathVariable("stockId") int stockId,
                                           @RequestParam("quantity") int quantity) {
        try {
            // 주식 매수 서비스 호출
            boolean success = stockService.buyStock(stockId, quantity);

            if (success) {
                return ResponseEntity.ok("주식 매수 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주식 매수 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
}