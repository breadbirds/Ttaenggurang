package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.item.Item;
import com.ladysparks.ttaenggrang.domain.item.ItemTransaction;
import com.ladysparks.ttaenggrang.dto.ItemDTO;
import com.ladysparks.ttaenggrang.dto.ItemTransactionDTO;
import com.ladysparks.ttaenggrang.mapper.ItemTransactionMapper;
import com.ladysparks.ttaenggrang.repository.ItemRepository;
import com.ladysparks.ttaenggrang.repository.ItemTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemTransactionService {

    private final ItemRepository itemRepository;
    private final ItemTransactionRepository itemTransactionRepository;
    private final ItemTransactionMapper itemTransactionMapper;

    @Autowired
    public ItemTransactionService(ItemRepository itemRepository, ItemTransactionRepository itemTransactionRepository, ItemTransactionMapper itemTransactionMapper) {
        this.itemRepository = itemRepository;
        this.itemTransactionRepository = itemTransactionRepository;
        this.itemTransactionMapper = itemTransactionMapper;
    }

    // 아이템 거래
    @Transactional
    public ItemTransactionDTO addItemTransaction(ItemTransactionDTO itemTransactionDTO) {
        // ✅ 1. 구매할 아이템 조회
        Item item = itemRepository.findById(itemTransactionDTO.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + itemTransactionDTO.getItemId()));

        // ✅ 2. 판매자 조회
        Long sellerId = item.getSeller().getId(); // Item 엔티티에 seller 정보가 있다고 가정
        Long buyerId = itemTransactionDTO.getBuyerId();

        // ✅ 3. 판매자와 구매자가 동일한 경우 거래 불가
        if (sellerId.equals(buyerId)) {
            throw new IllegalArgumentException("Seller and buyer cannot be the same person (User ID: " + buyerId + ")");
        }

        // ✅ 4. 남은 수량 계산
        int remainingQuantity = item.getQuantity() - itemTransactionDTO.getQuantity();

        // ✅ 5. 남은 수량이 0보다 작은 경우 구매 불가
        if (remainingQuantity < 0) {
            throw new IllegalArgumentException("Not enough stock for item with id: " + item.getId());
        }

        // ✅ 6. 재고 차감 후 업데이트
        item.updateQuantity(remainingQuantity);
        itemRepository.save(item);

        // ✅ 7. 거래 엔티티 변환 및 저장
        ItemTransaction itemTransaction = itemTransactionMapper.toEntity(itemTransactionDTO);
        ItemTransaction savedItemTransaction = itemTransactionRepository.save(itemTransaction);

        // ✅ 8. DTO 변환 후 반환
        return itemTransactionMapper.toDto(savedItemTransaction);
    }

    // 학생의 모든 판매 내역 조회
    public List<ItemTransactionDTO> findSaleItemTransactions(Long sellerId) {
        List<ItemTransaction> itemTransactions = itemTransactionRepository.findByItemSellerId(sellerId);
        return itemTransactions
                .stream()
                .map(itemTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    // 학생의 모든 구매 내역 조회
    public List<ItemTransactionDTO> findOrderItemTransactions(Long buyerId) {
        List<ItemTransaction> itemTransactions = itemTransactionRepository.findByBuyerId(buyerId);
        return itemTransactions
                .stream()
                .map(itemTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

}
