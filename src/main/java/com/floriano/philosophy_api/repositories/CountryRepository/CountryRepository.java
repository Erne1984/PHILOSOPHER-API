package com.floriano.philosophy_api.repositories.CountryRepository;

import com.floriano.philosophy_api.model.Country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
