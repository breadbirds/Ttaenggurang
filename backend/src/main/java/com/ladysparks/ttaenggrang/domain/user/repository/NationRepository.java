package com.ladysparks.ttaenggrang.domain.user.repository;

import com.ladysparks.ttaenggrang.domain.user.entity.Nation;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface NationRepository extends CrudRepository<Nation, Long> {
    Optional<Nation> findByTeacherId(Long teacherId);
}
