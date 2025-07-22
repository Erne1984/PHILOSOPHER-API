package com.floriano.login_system_backend_maven.repositories.CountryRepository;

import com.floriano.login_system_backend_maven.model.Country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
