package com.floriano.philosophy_api.repositories.PhilosopherRepository;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PhilosopherRepository extends JpaRepository<Philosopher, Long>, JpaSpecificationExecutor<Philosopher> {

    boolean existsByNameIgnoreCase(String authorName);

    List<Philosopher> findByCountryId(Long countryId);
}
