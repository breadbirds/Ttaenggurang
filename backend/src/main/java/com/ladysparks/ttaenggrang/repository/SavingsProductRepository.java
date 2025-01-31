package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.bank.SavingsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsProductRepository extends JpaRepository<SavingsProduct, Long> {

    List<SavingsProduct> findByTeacherId(Long teacherId);

    boolean existsByTeacherIdAndName(Long teacherId, String name);

}
