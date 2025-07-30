package com.floriano.philosophy_api.repositories.QuoteRepository;

import com.floriano.philosophy_api.model.Quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
