package com.floriano.philosophy_api.repositories.QuoteRepository;

import com.floriano.philosophy_api.model.Quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findByPhilosopherId(Long philosopherId);
    List<Quote> findByWorkId(Long workId);
}
