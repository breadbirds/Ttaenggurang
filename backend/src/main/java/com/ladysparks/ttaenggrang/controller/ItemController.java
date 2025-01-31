package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.ItemApiSpecification;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import com.ladysparks.ttaenggrang.response.ApiResponse;
import com.ladysparks.ttaenggrang.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // 아이템 판매 [등록]
    @PostMapping("/sale")
    public ResponseEntity<ApiResponse<ItemDTO>> itemAdd(@RequestBody ItemDTO itemDto) {
        ItemDTO savedItem = itemService.addItem(itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(savedItem));
    }

    // 아이템 판매 [조회]
    @GetMapping("/sale")
    public ResponseEntity<ApiResponse<List<ItemDTO>>> saleItemList(@RequestParam Long studentId) {
        List<ItemDTO> itemDtoList = itemService.findSaleItems(studentId);
        return ResponseEntity.ok(ApiResponse.success(itemDtoList));
    }

    // 아이템 상품 내역 [전체 조회]
    @GetMapping
    public ResponseEntity<ApiResponse<List<ItemDTO>>> itemList(@RequestParam Long teacherId) {
        List<ItemDTO> itemDTOList = itemService.findProductList(teacherId);
        return ResponseEntity.ok(ApiResponse.success(itemDTOList));
    }

    // 아이템 상품 내역 [상세 조회]
    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemDTO>> itemDetails(@PathVariable("itemId") Long itemId) {
        ItemDTO itemDTO = itemService.findProduct(itemId);
        return ResponseEntity.ok(ApiResponse.success(itemDTO));
    }

}

