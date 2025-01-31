package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.ItemApiSpecification;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import com.ladysparks.ttaenggrang.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController implements ItemApiSpecification {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 아이템 상품 내역 [전체 조회]
    @GetMapping
    public ResponseEntity<List<ItemDTO>> itemList(@RequestParam Long teacherId) {
        List<ItemDTO> itemDTOList = itemService.findProductList(teacherId);
        return itemDTOList.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(itemDTOList);
    }

    // 아이템 상품 내역 [상세 조회]
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> itemDetails(@PathVariable("itemId") Long itemId) {
        return itemService.findProduct(itemId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 아이템 판매 [등록]
    @PostMapping("/sale")
    public ResponseEntity<ItemDTO> itemAdd(@RequestBody ItemDTO itemDto) {
        return ResponseEntity.ok(itemService.addItem(itemDto));
    }

    // 아이템 판매 [조회]
    @GetMapping("/sale")
    public ResponseEntity<List<ItemDTO>> saleItemList(@RequestParam Long studentId) {
        List<ItemDTO> itemDtoList = itemService.findSaleItems(studentId);
        return itemDtoList.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(itemDtoList);
    }

}

