package com.ladysparks.ttaenggrang.domain.user.repository;

import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
}
