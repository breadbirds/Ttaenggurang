package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.ItemTransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Item-Transaction", description = "아이템 거래 관련 API")
public interface ItemTransactionApiSpecification {

    @Operation(summary = "아이템 구매", description = "💡아이템을 구매합니다.")
    ResponseEntity<ItemTransactionDTO> itemTransactionAdd(@RequestBody ItemTransactionDTO itemTransactionDTO);

    @Operation(summary = "아이템 판매 내역 전체 조회", description = "💡학생 ID로 학생의 아이템 판매 내역을 조회합니다.")
    ResponseEntity<List<ItemTransactionDTO>> saleItemTransactionList(@PathVariable("studentId") Long studentId);

    @Operation(summary = "아이템 구매 내역 전체 조회", description = "💡학생 ID로 학생의 아이템 구매 내역을 조회합니다.")
    ResponseEntity<List<ItemTransactionDTO>> orderItemTransactionList(@RequestParam Long studentId);

}
