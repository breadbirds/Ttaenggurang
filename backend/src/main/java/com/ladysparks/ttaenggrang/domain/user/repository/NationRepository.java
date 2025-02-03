package com.ladysparks.ttaenggrang.domain.user.repository;

import com.ladysparks.ttaenggrang.domain.user.entity.Nation;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface NationRepository extends CrudRepository<Nation, Long> {
    Optional<Nation> findByNationName(String nationName);
}
