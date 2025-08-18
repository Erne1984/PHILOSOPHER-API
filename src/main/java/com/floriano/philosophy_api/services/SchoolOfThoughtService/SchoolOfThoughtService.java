package com.floriano.philosophy_api.services.SchoolOfThoughtService;

import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtResponseDTO;
import com.floriano.philosophy_api.exceptions.SchoolOfThoghtNotFoundException;
import com.floriano.philosophy_api.mapper.SchoolOfThoughtMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.SchoolOfThoughtService.utils.SchoolOfThoughtDeleteHelper;
import com.floriano.philosophy_api.services.SchoolOfThoughtService.utils.SchoolOfThoughtUpdateHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolOfThoughtService {

    private final SchoolOfThoughtRepository schoolOfThoughtRepository;
    private final WorkRepository workRepository;
    private final PhilosopherRepository philosopherRepository;

    private SchoolOfThoughtService(SchoolOfThoughtRepository schoolOfThoughtRepository, WorkRepository workRepository, PhilosopherRepository philosopherRepository) {
        this.schoolOfThoughtRepository = schoolOfThoughtRepository;
        this.workRepository = workRepository;
        this.philosopherRepository = philosopherRepository;
    }

    public List<SchoolOfThoughtResponseDTO> getSchoolOfThoughts() {

        List<SchoolOfThought> schoolOfThoughts = schoolOfThoughtRepository.findAll();

        return schoolOfThoughts
                .stream()
                .map(SchoolOfThoughtMapper::toDTO)
                .toList();
    }

    public SchoolOfThoughtResponseDTO getSchoolOfThought(Long id) {
        SchoolOfThought schoolOfThought = schoolOfThoughtRepository.findById(id)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));

        return SchoolOfThoughtMapper.toDTO(schoolOfThought);
    }

    public SchoolOfThought createSchoolOfThought(SchoolOfThoughtRequestDTO  dto) {
        List<Work> works = workRepository.findAllById(dto.getWorksIds());

        List<Philosopher> philosophers = philosopherRepository.findAllById(dto.getPhilosophersIds());

        SchoolOfThought schoolOfThought = SchoolOfThoughtMapper.toEntity(dto, works, philosophers);

        return schoolOfThoughtRepository.save(schoolOfThought);
    }

    public SchoolOfThought updateSchoolOfThought(Long id, SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought school = schoolOfThoughtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School of Thought not found"));

        List<Work> works = workRepository.findAllById(dto.getWorksIds());
        List<Philosopher> philosophers = philosopherRepository.findAllById(dto.getPhilosophersIds());

        SchoolOfThoughtUpdateHelper.updateBasicFields(school, dto);
        SchoolOfThoughtUpdateHelper.updatePhilosophers(school, philosophers);
        SchoolOfThoughtUpdateHelper.updateWorks(school, works);

        return schoolOfThoughtRepository.save(school);
    }

    public SchoolOfThought deleteSchoolOfThought(Long id) {
        SchoolOfThought schoolOfThought = schoolOfThoughtRepository.findById(id)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School of Thought not found"));

        SchoolOfThoughtDeleteHelper.detachAllRelationShips(schoolOfThought);
        schoolOfThoughtRepository.save(schoolOfThought);
        schoolOfThoughtRepository.delete(schoolOfThought);

        return schoolOfThought;
    }
}
