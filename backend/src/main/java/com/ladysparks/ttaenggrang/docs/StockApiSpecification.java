package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.stock.StockDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Tag(name = "Stock", description = "주식 API")
public interface StockApiSpecification {

    @Operation(summary = "주식 전체 조회", description = "💡 전체 주식을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<StockDTO>> getStocks();


}
