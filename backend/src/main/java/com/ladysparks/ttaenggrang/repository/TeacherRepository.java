package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
