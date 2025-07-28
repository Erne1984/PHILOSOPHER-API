package com.floriano.philosophy_api.repositories.WorkRepository;


import com.floriano.philosophy_api.model.Work.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
}
