package com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository;

import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolOfThoughtRepository extends JpaRepository<SchoolOfThought, Long> {
}
