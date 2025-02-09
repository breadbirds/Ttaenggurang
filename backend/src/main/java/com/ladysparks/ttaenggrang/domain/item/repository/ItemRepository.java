package com.ladysparks.ttaenggrang.domain.item.repository;

import com.ladysparks.ttaenggrang.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 특정 교사가 담당하는 학생들이 판매한 아이템 조회
    @Query("SELECT i FROM Item i WHERE i.seller.teacher.id = :teacherId")
    List<Item> findItemsByTeacherId(@Param("teacherId") Long teacherId);

    List<Item> findBySellerId(Long studentId);

}