package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.ItemApiSpecification;
import com.ladysparks.ttaenggrang.domain.item.Item;
import com.ladysparks.ttaenggrang.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemController implements ItemApiSpecification {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        String result = "TEST";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 아이템 전체 조회
    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        List<Item> result = itemService.findItems();

        if (!result.isEmpty()) {
            logger.info("Items found: {}", result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        logger.warn("Items not found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 아이템 상세 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable("itemId") int itemId) {
        Optional<Item> result = itemService.findItem(itemId);

        if (result.isPresent()) {
            logger.info("Item found: {}", result.get());
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        logger.warn("Item not found for id: {}", itemId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 아이템 등록
    @PostMapping
    public ResponseEntity<Item> postItem(@RequestBody Item item) {
        int itemId = itemService.saveItem(item);
        item.setId(itemId);
        if (itemId > 0) {
            logger.info("Item ID: {}", itemId);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }

        logger.warn("Item not saved: {}", itemId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

