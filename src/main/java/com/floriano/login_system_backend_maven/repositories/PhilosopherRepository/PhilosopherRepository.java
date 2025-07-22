package com.floriano.login_system_backend_maven.repositories.PhilosopherRepository;

import com.floriano.login_system_backend_maven.model.Philosopher.Philosopher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhilosopherRepository extends JpaRepository<Philosopher, Long> {
}
