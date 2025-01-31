package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.item.Item;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import com.ladysparks.ttaenggrang.mapper.ItemMapper;
import com.ladysparks.ttaenggrang.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
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

    // 아이템 등록
    public ItemDTO addItem(ItemDTO itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDto(savedItem);
    }

    // 특정 교사의 상품 리스트 조회
    public List<ItemDTO> findProductList(Long teacherId) {
        return itemRepository.findByTeacherId(teacherId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    // 특정 상품 상세 조회 - 존재하지 않으면 예외 발생
    public ItemDTO findProduct(Long itemId) {
        return itemRepository.findById(itemId)
                .map(itemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다. ID: " + itemId));
    }

    // 특정 학생이 판매한 상품 조회
    public List<ItemDTO> findSaleItems(Long studentId) {
        return itemRepository.findBySellerId(studentId)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

}
