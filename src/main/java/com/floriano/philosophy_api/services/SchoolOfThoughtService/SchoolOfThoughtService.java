package com.floriano.philosophy_api.services.SchoolOfThoughtService;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtResponseDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.exceptions.SchoolOfThoghtNotFoundException;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.mapper.SchoolOfThoughtMapper;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.SchoolOfThoughtService.utils.SchoolOfThoughtDeleteHelper;
import com.floriano.philosophy_api.services.SchoolOfThoughtService.utils.SchoolOfThoughtUpdateHelper;
import com.floriano.philosophy_api.specification.SchoolOfThoughtSpecification;
import com.floriano.philosophy_api.specification.WorkSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<SchoolOfThoughtResponseDTO> getSchoolOfThoughts(Pageable pageable) {

        Page<SchoolOfThought> schoolOfThoughts = schoolOfThoughtRepository.findAll(pageable);

        return schoolOfThoughts.map(SchoolOfThoughtMapper::toDTO);
    }

    public SchoolOfThoughtResponseDTO getSchoolOfThought(Long id) {
        SchoolOfThought schoolOfThought = schoolOfThoughtRepository.findById(id)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));

        return SchoolOfThoughtMapper.toDTO(schoolOfThought);
    }

    public SchoolOfThought getSchoolOfThoughtById(Long id) {
        return schoolOfThoughtRepository.findById(id)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));
    }

    public Page<PhilosopherResponseDTO> getPhilosophersBySchool(Long id, Pageable pageable) {
        SchoolOfThought school = schoolOfThoughtRepository.findById(id)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));

        List<PhilosopherResponseDTO> dtos = school.getPhilosophers().stream()
                .map(PhilosopherMapper::toDTO)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());
        List<PhilosopherResponseDTO> paged = dtos.subList(start, end);

        return new PageImpl<>(paged, pageable, dtos.size());
    }

    public Page<WorkResponseDTO> getWorksBySchool(Long id, Pageable pageable) {
        SchoolOfThought school = schoolOfThoughtRepository.findById(id)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));

        List<WorkResponseDTO> dtos = school.getWorks().stream()
                .map(WorkMapper::toDTO)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());
        List<WorkResponseDTO> paged = dtos.subList(start, end);

        return new PageImpl<>(paged, pageable, dtos.size());
    }

    public Page<SchoolOfThoughtResponseDTO> searchSchoolOfThought(String title, Pageable pageable) {
        Specification<SchoolOfThought> spec = SchoolOfThoughtSpecification.hasName(title);

        return schoolOfThoughtRepository.findAll(spec, pageable).map(SchoolOfThoughtMapper::toDTO);
    }

    public void addPhilosopherToSchool(Long schoolId, Long philosopherId) {
        SchoolOfThought school = schoolOfThoughtRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));
        Philosopher philosopher = philosopherRepository.findById(philosopherId)
                .orElseThrow(() -> new RuntimeException("Philosopher not found"));

        school.addPhilosopher(philosopher);
        schoolOfThoughtRepository.save(school);
    }

    public void removePhilosopherFromSchool(Long schoolId, Long philosopherId) {
        SchoolOfThought school = schoolOfThoughtRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));
        Philosopher philosopher = philosopherRepository.findById(philosopherId)
                .orElseThrow(() -> new RuntimeException("Philosopher not found"));

        school.removePhilosopher(philosopher);
        schoolOfThoughtRepository.save(school);
    }

    public void addWorkToSchool(Long schoolId, Long workId) {
        SchoolOfThought school = schoolOfThoughtRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        school.addWork(work);
        schoolOfThoughtRepository.save(school);
    }

    public void removeWorkFromSchool(Long schoolId, Long workId) {
        SchoolOfThought school = schoolOfThoughtRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        school.removeWork(work);
        schoolOfThoughtRepository.save(school);
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
