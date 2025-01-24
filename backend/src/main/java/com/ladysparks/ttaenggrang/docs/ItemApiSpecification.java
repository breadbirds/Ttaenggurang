package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.domain.item.Item;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Item", description = "아이템 거래 관련 API")
public interface ItemApiSpecification {

    @Operation(summary = "아이템 전체 조회", description = "💡 전체 아이템을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItems();

    @Operation(summary = "아이템 상세 조회", description = "💡 아이템 ID로 아이템을 조회합니다.")
    @GetMapping
    public ResponseEntity<ItemDTO> getItem(@PathVariable("itemId") int itemId);

    @Operation(summary = "아이템 등록", description = "💡 판매할 아이템을 등록합니다.")
    @GetMapping
    public ResponseEntity<ItemDTO> postItem(@RequestBody ItemDTO itemDto);
    
}
