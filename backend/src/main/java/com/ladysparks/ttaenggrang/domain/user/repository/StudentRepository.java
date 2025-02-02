package com.ladysparks.ttaenggrang.domain.user.repository;

import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // 메서드 네이밍만 사용하면 Student 객체 전체가 반환 -> @Query 사용
//    Optional<Long> findBankAccountIdById(Long studentId);

    // ✅ 특정 학생(Student ID)의 BankAccount ID 조회 (JPQL 사용)
    @Query("SELECT s.bankAccount.id FROM Student s WHERE s.id = :studentId")
    Optional<Long> findBankAccountIdById(@Param("studentId") Long studentId);

}
