package com.floriano.philosophy_api.services.CountryService.utils;

import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;

public class CountryDeleteHelper {

    public static void detachAllRelationShips(Country country, PhilosopherRepository philosopherRepository, WorkRepository workRepository) {
        for (Philosopher p : country.getPhilosophers()) {
            p.setCountry(null);
        }

        for (Work w : country.getWorks()) {
            w.setCountry(null);
        }
        philosopherRepository.saveAll(country.getPhilosophers());
        workRepository.saveAll(country.getWorks());
    }
}
