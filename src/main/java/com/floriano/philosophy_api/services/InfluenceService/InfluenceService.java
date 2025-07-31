package com.floriano.philosophy_api.services.InfluenceService;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.mapper.InfluenceMapper;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.repositories.InfluenceRepository.InfluenceRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import org.springframework.stereotype.Service;

@Service
public class InfluenceService {

    private final InfluenceRepository influenceRepository;
    private final PhilosopherRepository philosopherRepository;

    public InfluenceService(InfluenceRepository influenceRepository, PhilosopherRepository philosopherRepository) {
        this.influenceRepository = influenceRepository;
        this.philosopherRepository = philosopherRepository;
    }

    public Influence createInfluence(InfluenceRequestDTO dto) {

        Philosopher influencer = philosopherRepository.findById(dto.getInfluencerId())
                .orElseThrow(() -> new IllegalArgumentException("Filósofo influencer não encontrado"));

        Philosopher influenced = philosopherRepository.findById(dto.getInfluencedId())
                .orElseThrow(() -> new IllegalArgumentException("Filósofo influenced não encontrado"));

        Influence influence = InfluenceMapper.toEntity(dto, influencer, influenced);

        return influenceRepository.save(influence);
    }
}
