package com.ladysparks.ttaenggrang.domain.stock.controller;

import com.ladysparks.ttaenggrang.domain.stock.dto.StockTransactionDTO;
import com.ladysparks.ttaenggrang.global.docs.StockApiSpecification;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockDTO;
import com.ladysparks.ttaenggrang.domain.stock.service.StockService;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import jakarta.validation.Valid;
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

    //주식 등록

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<StockDTO>> addStock(@RequestBody StockDTO stockDTO) {
        // 주식 등록 서비스 호출
        StockDTO createdStockDTO = stockService.registerStock(stockDTO);

        // 주식 등록 후 성공적인 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(createdStockDTO));
    }



    // 주식 목록 전체 조회
    @GetMapping
    public ResponseEntity<List<StockDTO>> getStocks() {
        List<StockDTO> result = stockService.findStocks(); // 모든 주식 정보를 반환
        return ResponseEntity.ok(result); // HTTP 200 OK와 함께 결과 반환
    }

    // 주식 상세 조회
    @GetMapping("/{stockId}")
    public ResponseEntity<StockDTO> getStock(@PathVariable("stockId") Long stockId) {
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
    public ResponseEntity<ApiResponse<StockTransactionDTO>> buyStock(@PathVariable("stockId") Long stockId,
                                                                     @RequestParam("share_count") int shareCount,
                                                                     @RequestParam("studentId") Long studentId) {

        // 주식 매수 서비스 호출
        StockTransactionDTO dto = stockService.buyStock(stockId, shareCount, studentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(dto));
    }

    //주식 매도
    @PostMapping("/{stockId}/sell")
    public ResponseEntity<ApiResponse<StockTransactionDTO>> sellStock(@PathVariable("stockId") Long stockId,
                                                                     @RequestParam("share_count") int shareCount,
                                                                     @RequestParam("studentId") Long studentId) {

        // 주식 매수 서비스 호출
        StockTransactionDTO dto = stockService.sellStock(stockId, shareCount, studentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(dto));
    }

//    // 가격 변동 (관리자가 호출)
//    @PostMapping("/{stockId}/update-price")
//    public ResponseEntity<ApiResponse<StockDTO>> updateStockPrice(
//            @PathVariable("stockId") Long stockId) {
//
//        // 주식 가격 업데이트 서비스 호출
//        StockDTO updatedStock = stockService.updateStockPrice(stockId);
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(updatedStock));
//    }
//

//    // 주식장 열기
//    @PostMapping("/open")
//    public ResponseEntity<String> openMarket() {
//        stockService.openMarket();
//        return ResponseEntity.ok("주식장이 열렸습니다.");
//    }
//
//    // 주식장 닫기
//    @PostMapping("/close")
//    public ResponseEntity<String> closeMarket() {
//        stockService.closeMarket();
//        return ResponseEntity.ok("주식장이 닫혔습니다.");
//    }






}