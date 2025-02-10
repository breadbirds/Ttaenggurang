package com.ladysparks.ttaenggrang.domain.item.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.BankTransactionDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransaction;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankTransactionType;
import com.ladysparks.ttaenggrang.domain.bank.service.BankTransactionService;
import com.ladysparks.ttaenggrang.domain.item.entity.Item;
import com.ladysparks.ttaenggrang.domain.item.entity.ItemTransaction;
import com.ladysparks.ttaenggrang.domain.item.dto.ItemTransactionDTO;
import com.ladysparks.ttaenggrang.domain.item.mapper.ItemTransactionMapper;
import com.ladysparks.ttaenggrang.domain.item.repository.ItemRepository;
import com.ladysparks.ttaenggrang.domain.item.repository.ItemTransactionRepository;
import com.ladysparks.ttaenggrang.domain.user.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemTransactionService {

    private final ItemRepository itemRepository;
    private final ItemTransactionRepository itemTransactionRepository;
    private final ItemTransactionMapper itemTransactionMapper;
    private final StudentService studentService;
    private final BankTransactionService bankTransactionService;

    // 아이템 거래
    @Transactional
    public ItemTransactionDTO addItemTransaction(ItemTransactionDTO itemTransactionDTO) {
        // 1. 구매할 아이템 조회
        Item item = itemRepository.findById(itemTransactionDTO.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이템을 찾을 수 없습니다. ID: " + itemTransactionDTO.getItemId()));

        // 2. 판매자와 구매자 조회
        Long sellerId = item.getSeller().getId();
        Long buyerId = studentService.getCurrentStudentId();

        // 3. 판매자와 구매자가 동일한 경우 거래 불가
        if (sellerId.equals(buyerId)) {
            throw new IllegalArgumentException("판매자와 구매자는 동일할 수 없습니다. (사용자 ID: " + buyerId + ")");
        }

        // 4. 남은 수량 계산 및 검증
        int remainingQuantity = item.getQuantity() - itemTransactionDTO.getQuantity();
        if (remainingQuantity < 0) {
            throw new IllegalArgumentException("아이템 재고가 부족합니다. 재고: " + item.getQuantity());
        }

        // 5. 재고 차감 후 업데이트
        item.updateQuantity(remainingQuantity);
        itemRepository.save(item);

        // 6. 거래 엔티티 변환 및 저장
        itemTransactionDTO.setBuyerId(buyerId);
        ItemTransaction itemTransaction = itemTransactionMapper.toEntity(itemTransactionDTO);
        ItemTransaction savedItemTransaction = itemTransactionRepository.save(itemTransaction);

        // 7. 은행 계좌 거래
        BankTransactionDTO bankTransactionDTO = BankTransactionDTO.builder()
                .bankAccountId(buyerId)
                .type(BankTransactionType.ITEM)
                .amount(itemTransactionDTO.getQuantity() * item.getPrice())
                .description("[아이템 거래] 상품명: " + item.getName())
                .receiverId(sellerId)
                .build();
        bankTransactionService.addBankTransaction(bankTransactionDTO);

        return itemTransactionMapper.toDto(savedItemTransaction);
    }

    // 학생의 모든 판매 내역 조회
    public List<ItemTransactionDTO> findItemTransactionsBySeller() {
        Long sellerId = studentService.getCurrentStudentId();
        return itemTransactionRepository.findByItemSellerId(sellerId)
                .stream()
                .map(itemTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    // 학생의 모든 구매 내역 조회
    public List<ItemTransactionDTO> findItemTransactionsByBuyer() {
        Long buyerId = studentService.getCurrentStudentId();
        return itemTransactionRepository.findByBuyerId(buyerId)
                .stream()
                .map(itemTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

}
