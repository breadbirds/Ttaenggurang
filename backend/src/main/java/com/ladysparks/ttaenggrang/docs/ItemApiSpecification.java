package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.domain.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ItemApiSpecification {

    @Tag(name = "Item", description = "아이템 거래 관련 API")
    @Operation(summary = "아이템 조회", description = "💡 아이템 ID로 아이템을 조회합니다.")
    @GetMapping
    public ResponseEntity<Item> getItem(@PathVariable("itemId") int itemId);

    @Tag(name = "Item", description = "아이템 거래 관련 API")
    @Operation(summary = "아이템 등록", description = "💡 판매할 아이템을 등록합니다.")
    @GetMapping
    public ResponseEntity<Item> postItem(@RequestBody Item item);
    
}
