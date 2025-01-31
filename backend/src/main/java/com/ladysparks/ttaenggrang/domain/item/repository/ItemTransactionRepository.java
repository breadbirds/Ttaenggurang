package com.ladysparks.ttaenggrang.domain.item.repository;

import com.ladysparks.ttaenggrang.domain.item.entity.ItemTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemTransactionRepository extends JpaRepository<ItemTransaction, Long> {

    // ✅ 특정 판매자가 판매한 모든 거래 조회
    List<ItemTransaction> findByItemSellerId(Long sellerId);

    List<ItemTransaction> findByBuyerId(Long buyerId);

}

