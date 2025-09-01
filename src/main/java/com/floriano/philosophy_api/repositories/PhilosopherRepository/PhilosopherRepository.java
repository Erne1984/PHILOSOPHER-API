package com.floriano.philosophy_api.repositories.PhilosopherRepository;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhilosopherRepository extends JpaRepository<Philosopher, Long>, JpaSpecificationExecutor<Philosopher> {

    boolean existsByNameIgnoreCase(String authorName);

    List<Philosopher> findByCountryId(Long countryId);

    @Query(value = "SELECT * FROM philosophers ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Philosopher findRandomPhilosopher();
}
