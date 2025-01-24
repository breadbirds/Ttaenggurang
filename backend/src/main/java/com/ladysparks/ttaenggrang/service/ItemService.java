package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.item.Item;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import com.ladysparks.ttaenggrang.repository.ItemRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRespository itemRepository;

    @Autowired
    public ItemService(ItemRespository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public int saveItem(ItemDTO itemDto) {
        Item item = ItemDTO.toEntity(itemDto);
        itemRepository.save(item);
        return item.getId();
    }

    public List<ItemDTO> findItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(ItemDTO::fromEntity) // Entity → DTO 변환
                .collect(Collectors.toList());
    }

    public Optional<ItemDTO> findItem(int itemId) {
        return itemRepository.findById(itemId)
                .map(ItemDTO::fromEntity);
    }

}
