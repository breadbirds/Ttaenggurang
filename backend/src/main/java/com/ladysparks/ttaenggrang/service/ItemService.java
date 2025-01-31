package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.item.Item;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import com.ladysparks.ttaenggrang.mapper.ItemMapper;
import com.ladysparks.ttaenggrang.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDTO addItem(ItemDTO itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDto(savedItem);
    }

    public List<ItemDTO> findProductList(Long teacherId) {
        return itemRepository.findByTeacherId(teacherId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ItemDTO> findProduct(Long itemId) {
        return itemRepository.findById(itemId)
                .map(itemMapper::toDto);
    }

    public List<ItemDTO> findSaleItems(Long studentId) {
        return itemRepository.findBySellerId(studentId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

}
