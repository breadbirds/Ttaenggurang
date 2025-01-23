package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRespository extends JpaRepository<Item, Integer> {}
