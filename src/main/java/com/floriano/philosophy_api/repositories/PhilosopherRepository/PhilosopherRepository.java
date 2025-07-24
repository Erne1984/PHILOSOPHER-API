package com.floriano.philosophy_api.repositories.PhilosopherRepository;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhilosopherRepository extends JpaRepository<Philosopher, Long> {

    boolean existsByNameIgnoreCase(String authorName);
}
