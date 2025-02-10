package com.ladysparks.ttaenggrang.domain.item.mapper;

import com.ladysparks.ttaenggrang.global.config.MapStructConfig;
import com.ladysparks.ttaenggrang.domain.item.entity.Item;
import com.ladysparks.ttaenggrang.domain.item.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    // Entity → DTO 변환
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "seller.name", target = "sellerName")
    ItemDTO toDto(Item bankAccount);

    // DTO → Entity 변환
    @Mapping(target = "id", ignore = true) // ID는 자동 생성되므로 무시
    @Mapping(source = "sellerId", target = "seller.id")
    Item toEntity(ItemDTO itemDTO);

}
