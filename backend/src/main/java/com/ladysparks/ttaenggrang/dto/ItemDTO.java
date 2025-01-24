package com.ladysparks.ttaenggrang.dto;

import com.ladysparks.ttaenggrang.domain.item.Item;
import lombok.*;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data // @Getter + @Setter
public class ItemDTO {

    private int id;
    private String name;
    private String description;
    private String image;
    private int price;
    private int quantity;
    private boolean isApproved;
    private Timestamp createAt;
    private Timestamp updateAt;

    public static ItemDTO fromEntity(Item item) {
        return new ItemDTOBuilder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .image(item.getImage())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .isApproved(item.isApproved())
                .createAt(item.getCreateAt())
                .updateAt(item.getUpdateAt())
                .build();
    }

    // lombok을 사용한 Entity 객체 생성(builder) 방법
    public static Item toEntity(ItemDTO itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .image(itemDto.getImage())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .isApproved(itemDto.isApproved())
                .createAt(itemDto.getCreateAt())
                .updateAt(itemDto.getUpdateAt())
                .build();
    }

}
