package com.floriano.philosophy_api.repositories.InfluenceRepository;

import com.floriano.philosophy_api.model.Influence.Influence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluenceRepository extends JpaRepository<Influence, Long> {
}
