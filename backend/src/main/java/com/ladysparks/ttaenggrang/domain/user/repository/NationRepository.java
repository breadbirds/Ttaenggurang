package com.ladysparks.ttaenggrang.domain.user.repository;

import com.ladysparks.ttaenggrang.domain.user.entity.Nation;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface NationRepository extends CrudRepository<Nation, Long> {
    Optional<Nation> findByTeacher_Id(Long teacherId);

    @Modifying  // 데이터 수정/삭제 작업에 필요
    @Transactional
    @Query("DELETE FROM Nation n WHERE n.teacher.id = :teacherId")
    void deleteByTeacher_Id(Long teacherId);
}
