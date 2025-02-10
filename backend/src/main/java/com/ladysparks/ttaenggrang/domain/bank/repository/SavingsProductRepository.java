package com.ladysparks.ttaenggrang.domain.bank.repository;

import com.ladysparks.ttaenggrang.domain.bank.entity.SavingsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SavingsProductRepository extends JpaRepository<SavingsProduct, Long> {

    List<SavingsProduct> findByTeacherId(Long teacherId);

    boolean existsByTeacherIdAndName(Long teacherId, String name);

    @Query("SELECT s.durationWeeks FROM SavingsProduct s WHERE s.id = :savingsProductId")
    Optional<Long> findDurationWeeksById(@Param("savingsProductId") Long savingsProductId);

}
