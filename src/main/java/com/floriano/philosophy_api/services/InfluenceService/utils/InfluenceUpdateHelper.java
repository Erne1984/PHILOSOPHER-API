package com.floriano.philosophy_api.services.InfluenceService.utils;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Influence.InfluenceStrength;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;

public class InfluenceUpdateHelper {

    public static void updateBasicFields(Influence influence, InfluenceRequestDTO dto) {
        if (dto.getStrength() != null && !dto.getStrength().trim().isEmpty()) {
            try {
                InfluenceStrength strengthEnum = InfluenceStrength.valueOf(dto.getStrength().trim().toUpperCase());
                influence.setStrength(strengthEnum);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid strength value: " + dto.getStrength());
            }
        }
    }

    public static void updateInfluencer(Influence influence, InfluenceRequestDTO dto, PhilosopherRepository philosopherRepository) {
        if (dto.getInfluencerId() != null) {
            Philosopher p  = philosopherRepository.findById(dto.getInfluencerId())
                    .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher not found"));
            influence.setInfluencer(p);
        }
    }

    public static void updateInfluenced(Influence influence, InfluenceRequestDTO dto, PhilosopherRepository philosopherRepository) {
        if (dto.getInfluencedId() != null) {
            Philosopher p  = philosopherRepository.findById(dto.getInfluencedId())
                    .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher not found"));
            influence.setInfluenced(p);
        }
    }

}
