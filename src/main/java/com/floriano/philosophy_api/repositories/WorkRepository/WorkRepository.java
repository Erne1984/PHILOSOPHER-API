package com.floriano.philosophy_api.repositories.WorkRepository;


import com.floriano.philosophy_api.model.Work.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>, JpaSpecificationExecutor<Work> {

    Page<Work> findByPhilosopherId(Long id, Pageable pageable);
    Page<Work> findByCountryId(Long id, Pageable pageable);
}
