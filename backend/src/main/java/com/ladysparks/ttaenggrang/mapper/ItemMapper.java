package com.ladysparks.ttaenggrang.mapper;

import com.ladysparks.ttaenggrang.config.MapStructConfig;
import com.ladysparks.ttaenggrang.domain.item.Item;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    // Entity → DTO 변환
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "teacher.id", target = "teacherId")
    ItemDTO toDto(Item bankAccount);

    // DTO → Entity 변환
    @Mapping(target = "id", ignore = true) // ID는 자동 생성되므로 무시
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(source = "teacherId", target = "teacher.id")
    Item toEntity(ItemDTO itemDTO);

}
