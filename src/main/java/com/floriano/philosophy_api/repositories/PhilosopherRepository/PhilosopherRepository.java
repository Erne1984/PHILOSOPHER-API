package com.floriano.philosophy_api.repositories.PhilosopherRepository;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhilosopherRepository extends JpaRepository<Philosopher, Long> {

    boolean existsByNameIgnoreCase(String authorName);

    List<Philosopher> findByCountryId(Long countryId);
}
