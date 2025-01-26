package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByTeacherId(Long teacherId);

    List<Item> findBySellerId(Long studentId);

}