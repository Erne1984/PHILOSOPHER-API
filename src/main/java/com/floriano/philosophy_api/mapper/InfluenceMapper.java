package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Influence.InfluenceStrength;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;

public class InfluenceMapper {

    public static Influence toEntity(InfluenceRequestDTO dto, Philosopher influencer, Philosopher influenced) {
        Influence influence = new Influence();

        influence.setInfluencer(influencer);
        influence.setInfluenced(influenced);

        try {
            InfluenceStrength strength = InfluenceStrength.valueOf(dto.getStrength().toUpperCase());
            influence.setStrength(strength);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Valor inválido para InfluenceStrength: " + dto.getStrength());
        }

        return influence;
    }

    public static InfluenceResponseDTO toDTO(Influence influence) {
        return new InfluenceResponseDTO(
                influence.getId(),
                influence.getInfluencer().getName(),
                influence.getInfluenced().getName(),
                influence.getStrength().getDescription()
        );
    }
}
