package com.ladysparks.ttaenggrang.repository;

import com.ladysparks.ttaenggrang.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRespository extends JpaRepository<Item, Integer> {}
