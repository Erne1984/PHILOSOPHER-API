package com.floriano.philosophy_api.services.PhilosopherService.utils;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;

public class PhilosopherDeleteHelper {

    public static void detachAllRelationships(Philosopher philosopher) {

        philosopher.clearThemes();
        philosopher.clearSchoolOfThought();

        if (philosopher.getQuotes() != null) {
            philosopher.getQuotes().forEach(q -> q.setPhilosopher(null));
            philosopher.getQuotes().clear();
        }

        if (philosopher.getWorks() != null) {
            philosopher.getWorks().forEach(w -> w.setPhilosopher(null));
            philosopher.getWorks().clear();
        }

        if (philosopher.getInfluenced() != null) {
            philosopher.getInfluenced().forEach(influence -> influence.setInfluencer(null));
            philosopher.getInfluenced().clear();
        }
        if (philosopher.getInfluencedBy() != null) {
            philosopher.getInfluencedBy().forEach(influence -> influence.setInfluenced(null));
            philosopher.getInfluencedBy().clear();
        }

        philosopher.setCountry(null);
    }
}
