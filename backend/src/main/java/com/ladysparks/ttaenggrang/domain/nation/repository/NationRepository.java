package com.ladysparks.ttaenggrang.domain.nation.repository;

import com.ladysparks.ttaenggrang.domain.nation.entity.Nation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NationRepository extends CrudRepository<Nation, Long>{}
