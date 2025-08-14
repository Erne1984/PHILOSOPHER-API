package com.floriano.philosophy_api.services.InfluenceService;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.exceptions.InfluenceNotFoundException;
import com.floriano.philosophy_api.mapper.InfluenceMapper;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.repositories.InfluenceRepository.InfluenceRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.services.InfluenceService.utils.InfluenceDeleteHelper;
import com.floriano.philosophy_api.services.InfluenceService.utils.InfluenceUpdateHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfluenceService {

    private final InfluenceRepository influenceRepository;
    private final PhilosopherRepository philosopherRepository;

    public InfluenceService(InfluenceRepository influenceRepository, PhilosopherRepository philosopherRepository) {
        this.influenceRepository = influenceRepository;
        this.philosopherRepository = philosopherRepository;
    }

    public List<Influence> getInfluences() {

        return influenceRepository.findAll();
    }

    public Influence createInfluence(InfluenceRequestDTO dto) {

        Philosopher influencer = philosopherRepository.findById(dto.getInfluencerId())
                .orElseThrow(() -> new IllegalArgumentException("Filósofo influencer não encontrado"));

        Philosopher influenced = philosopherRepository.findById(dto.getInfluencedId())
                .orElseThrow(() -> new IllegalArgumentException("Filósofo influenced não encontrado"));

        Influence influence = InfluenceMapper.toEntity(dto, influencer, influenced);

        return influenceRepository.save(influence);
    }

    public Influence updateInfluence(Long id, InfluenceRequestDTO dto) {
        Influence influence = influenceRepository.findById(id)
                .orElseThrow(() -> new InfluenceNotFoundException("Influence not found"));

        InfluenceUpdateHelper.updateBasicFields(influence, dto);
        InfluenceUpdateHelper.updateInfluencer(influence, dto, philosopherRepository);
        InfluenceUpdateHelper.updateInfluenced(influence, dto, philosopherRepository);

        return influenceRepository.save(influence);
    }

    public Influence deleteInfluence(Long id) {
        Influence influence = influenceRepository.findById(id)
                .orElseThrow(() -> new InfluenceNotFoundException("Influence not found"));

        InfluenceDeleteHelper.detachAllRelationships(influence);

        influenceRepository.save(influence);
        influenceRepository.delete(influence);

        return influence;
    }

}
