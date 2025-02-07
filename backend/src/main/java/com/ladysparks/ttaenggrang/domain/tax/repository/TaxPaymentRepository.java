package com.ladysparks.ttaenggrang.domain.tax.repository;

import com.ladysparks.ttaenggrang.domain.tax.entity.TaxPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaxPaymentRepository extends JpaRepository<TaxPayment, Long> {

    @Query("SELECT t FROM TaxPayment t WHERE t.student.id = :studentId")
    List<TaxPayment> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT t FROM TaxPayment t WHERE t.tax.id = :taxId")
    List<TaxPayment> findByTaxId(@Param("taxId") Long taxId);

    @Query("SELECT t FROM TaxPayment t WHERE t.student.teacher.id = :teacherId")
    List<TaxPayment> findByTeacherId(@Param("teacherId") Long teacherId);

}
