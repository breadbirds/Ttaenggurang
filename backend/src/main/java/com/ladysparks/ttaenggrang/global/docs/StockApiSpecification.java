package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.stock.dto.OpenResponseDTO;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockDTO;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockTransactionDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Stock", description = "주식 API")
public interface StockApiSpecification {

    @Operation(summary = "주식 전체 조회", description = "💡 전체 주식을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<StockDTO>> getStocks();

    @Operation(summary = "주식 상세 조회", description = "💡 주식 ID로 주식을 조회합니다.")
    @GetMapping
    public ResponseEntity<StockDTO> getStock(@PathVariable("stockId") Long stockId);

    @Operation(summary = "주식 등록", description = "주식을 등록 합니다")
    @PostMapping
    public ResponseEntity<ApiResponse<StockDTO>> addStock(@RequestBody StockDTO stockDto);


    @Operation(summary = "주식 매수", description = "💡 주식 ID와 수량으로 주식을 매수합니다.")
    @PostMapping("/{stockId}/buy")
    public ResponseEntity<ApiResponse<StockTransactionDTO>> buyStock(@PathVariable("stockId") Long stockId,
                                                                     @RequestParam("share_count") int shareCount,
                                                                     @RequestParam("studentId") Long studentId);

    @Operation(summary = "주식 매도", description = "💡 주식 ID와 수량으로 주식을 매도합니다.")
    @PostMapping("/{stockId}/sell")
    public ResponseEntity<ApiResponse<StockTransactionDTO>> sellStock(@PathVariable("stockId") Long stockId,
                                                                     @RequestParam("share_count") int shareCount,
                                                                     @RequestParam("studentId") Long studentId);

//    @Operation(summary = "변동률", description = "💡 주식 변동률 조회")
//    public ResponseEntity<ApiResponse<StockDTO>> updateStockPrice(
//            @PathVariable("stockId") Long stockId);
//    @Operation(summary = "주식장 열기", description = "💡 주식장 열려라 참께")
//    @PostMapping("/open")
//    public ResponseEntity<String> openMarket();
//
//    @Operation(summary = "주식장 닫기", description = "💡 주식장 닫혀라 참께")
//    @PostMapping("/close")
//    public ResponseEntity<String> closeMarket();
//

    @Operation(summary = "주식 OPEN", description = "💡 주식장 열림")
    @PostMapping("/open")
    public ResponseEntity<String> openMarket();

    @Operation(summary = "주식 CLOSE", description = "💡주식장 닫힘")
    @PostMapping("/close")
    public ResponseEntity<String> closeMarket();

}