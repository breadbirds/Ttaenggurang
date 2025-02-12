package com.ladysparks.ttaenggrang.domain.stock.controller;

import com.ladysparks.ttaenggrang.domain.stock.dto.OpenResponseDTO;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockTransactionDTO;
import com.ladysparks.ttaenggrang.domain.stock.dto.StudentStockDTO;
import com.ladysparks.ttaenggrang.global.docs.StockApiSpecification;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockDTO;
import com.ladysparks.ttaenggrang.domain.stock.service.StockService;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<ApiResponse<StockDTO>> getStock(@PathVariable("stockId") Long stockId) {
        Optional<StockDTO> result = stockService.findStock(stockId);

        // 값이 없으면 404 Not Found 반환
        if (result.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success(result.get())); // 값이 있으면 200 OK와 함께 결과 반환
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
    //학생 보유 주식 조회
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentStockDTO>> getStudentStocks(@PathVariable Long studentId) {
        List<StudentStockDTO> stockList = stockService.getStudentStocks(studentId);
        return ResponseEntity.ok(stockList);
    }



    // 주식장 활성화 / 비활성화
    @PostMapping("/manage")
    public ResponseEntity<Map<String, Boolean>> manageStockMarket(@RequestParam boolean openMarket) {
        // 주식 시장 관리 서비스 호출
        boolean marketStatus = stockService.manageMarket(openMarket);

        // 응답 데이터 준비
        Map<String, Boolean> response = new HashMap<>();
        response.put("isMarketActive", marketStatus);

        // 응답 반환
        return ResponseEntity.ok(response);
    }

    // 주식 활성화/ 비활성화 버튼 조회
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Boolean>> checkMarketStatus() {
        boolean isMarketOpen = stockService.isMarketOpen();
        return ResponseEntity.ok(ApiResponse.success(isMarketOpen)); // true이면 열림, false이면 닫힘
    }


    // 주식 개장, 폐장 시장 변경
    @PutMapping("/update-market-time")
    public ResponseEntity<ApiResponse<StockDTO>> updateMarketTimeForAllStocks(@RequestBody StockDTO stockDTO) {
        LocalTime newOpenTime = stockDTO.getOpenTime();
        LocalTime newCloseTime = stockDTO.getCloseTime();

        // 시간 값이 제대로 파싱되었는지 확인
        if (newOpenTime == null || newCloseTime == null) {
            return ResponseEntity.badRequest().build();
        }

        stockService.updateMarketTimeForAllStocks(newOpenTime, newCloseTime);

        // 성공적으로 변경된 StockDTO 객체 반환
        stockDTO.setOpenTime(newOpenTime);
        stockDTO.setCloseTime(newCloseTime);
        return ResponseEntity.ok(ApiResponse.success(stockDTO));
    }
    // 주식의 폐장 시간만 변경하는 메서드
//    @PutMapping("/{stockId}/update-close-time")
//    public ResponseEntity<String> updateCloseTime(
//            @PathVariable Long stockId,
//            @RequestParam("closeTime") @DateTimeFormat(pattern = "HH:mm") LocalTime newCloseTime) {
//
//        stockService.updateCloseTime(stockId, newCloseTime);
//        return ResponseEntity.ok(stockId + "의 폐장 시간이 " + newCloseTime + "로 변경되었습니다.");
//    }
}







