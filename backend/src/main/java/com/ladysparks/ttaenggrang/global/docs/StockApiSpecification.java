package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.stock.dto.StockDTO;
import com.ladysparks.ttaenggrang.domain.stock.dto.StockTransactionDTO;
import com.ladysparks.ttaenggrang.domain.stock.repository.StockRepository;
import com.ladysparks.ttaenggrang.domain.stock.service.StockService;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<StockDTO> getStock(@PathVariable("stockId") int stockId);

    @Operation(summary = "주식 매수", description = "💡 주식 ID와 수량으로 주식을 매수합니다.")
    @PostMapping("/{stockId}/buy")
    public ResponseEntity<ApiResponse<StockTransactionDTO>> buyStock(@PathVariable("stockId") int stockId,
                                                                     @RequestParam("share_count") int shareCount,
                                                                     @RequestParam("studentId") Long studentId);

}