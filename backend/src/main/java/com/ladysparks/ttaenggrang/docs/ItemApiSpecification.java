package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.ItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Item", description = "아이템 관련 API")
public interface ItemApiSpecification {

    @Operation(summary = "반 내 아이템 [전체 조회]", description = "💡 교사 ID로 반 내 전체 아이템을 조회합니다.")
    ResponseEntity<List<ItemDTO>> itemList(@RequestParam(required = false) Long teacherId);

    @Operation(summary = "반 내 아이템 [상세 조회]", description = "💡 아이템 ID로 아이템을 조회합니다.")
    ResponseEntity<ItemDTO> itemDetails(@PathVariable("itemId") Long itemId);

    @Operation(summary = "판매 아이템 [등록]", description = "💡 판매할 아이템을 등록합니다.")
    ResponseEntity<ItemDTO> itemAdd(@RequestBody ItemDTO itemDto);

    @Operation(summary = "판매 아이템 [전체 조회]", description = "💡 학생 ID로 판매 중인 아이템을 조회합니다.")
    ResponseEntity<List<ItemDTO>> saleItemList(@RequestParam(required = false) Long studentId);

}