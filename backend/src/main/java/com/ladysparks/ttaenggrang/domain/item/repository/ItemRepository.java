package com.ladysparks.ttaenggrang.domain.item.repository;

import com.ladysparks.ttaenggrang.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByTeacherId(Long teacherId);

    List<Item> findBySellerId(Long studentId);

}