package com.ladysparks.ttaenggrang.domain.item.controller;

import com.ladysparks.ttaenggrang.global.docs.ItemTransactionApiSpecification;
import com.ladysparks.ttaenggrang.domain.item.dto.ItemTransactionDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.domain.item.service.ItemTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-transactions")
public class ItemTransactionController implements ItemTransactionApiSpecification {

    private final ItemTransactionService itemTransactionService;

    @Autowired
    public ItemTransactionController(ItemTransactionService itemTransactionService) {
        this.itemTransactionService = itemTransactionService;
    }

    // 아이템 구매 [등록]
    @PostMapping
    public ResponseEntity<ApiResponse<ItemTransactionDTO>> itemTransactionAdd(@RequestBody @Valid ItemTransactionDTO itemTransactionDTO) {
        ItemTransactionDTO savedTransaction = itemTransactionService.addItemTransaction(itemTransactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(savedTransaction));
    }

    // 아이템 판매 내역 [전체 조회]
    @GetMapping("/sale")
    public ResponseEntity<ApiResponse<List<ItemTransactionDTO>>> itemTransactionBySellerList() {
        List<ItemTransactionDTO> itemTransactionDTOList = itemTransactionService.findItemTransactionsBySeller();
        return ResponseEntity.ok(ApiResponse.success(itemTransactionDTOList));
    }

    // 아이템 구매 내역 [전체 조회]
    @GetMapping("/purchase")
    public ResponseEntity<ApiResponse<List<ItemTransactionDTO>>> itemTransactionByBuyerList() {
        List<ItemTransactionDTO> itemTransactionDTOList = itemTransactionService.findItemTransactionsByBuyer();
        return ResponseEntity.ok(ApiResponse.success(itemTransactionDTOList));
    }

}