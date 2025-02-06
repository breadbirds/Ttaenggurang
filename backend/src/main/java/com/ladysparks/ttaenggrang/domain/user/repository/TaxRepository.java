package com.ladysparks.ttaenggrang.domain.user.repository;

import com.ladysparks.ttaenggrang.domain.user.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TaxRepository  extends JpaRepository<Tax, Long> {
    Optional<Tax> findByTaxName(String taxName);
}
