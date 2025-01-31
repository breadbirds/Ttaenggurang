package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.ItemTransactionApiSpecification;
import com.ladysparks.ttaenggrang.dto.ItemTransactionDTO;
import com.ladysparks.ttaenggrang.service.ItemTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 아이템 거래 [등록]
    @PostMapping
    public ResponseEntity<ItemTransactionDTO> itemTransactionAdd(@RequestBody ItemTransactionDTO itemTransactionDTO) {
        return ResponseEntity.ok(itemTransactionService.addItemTransaction(itemTransactionDTO));
    }

    // 아이템 판매 내역 [전체 조회]
    @GetMapping("/sale")
    public ResponseEntity<List<ItemTransactionDTO>> saleItemTransactionList(@RequestParam Long studentId) {
        List<ItemTransactionDTO> itemTransactionDTOList = itemTransactionService.findSaleItemTransactions(studentId);
        return itemTransactionDTOList.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(itemTransactionDTOList);
    }

    // 아이템 구매 내역 [전체 조회]
    @GetMapping("/order")
    public ResponseEntity<List<ItemTransactionDTO>> orderItemTransactionList(@RequestParam Long studentId) {
        List<ItemTransactionDTO> itemTransactionDTOList = itemTransactionService.findOrderItemTransactions(studentId);
        return itemTransactionDTOList.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(itemTransactionDTOList);
    }

}