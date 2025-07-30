package com.floriano.philosophy_api.services.SchoolOfThoughtService;

import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.mapper.SchoolOfThoughtMapper;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolOfThoughtService {

    private final SchoolOfThoughtRepository schoolOfThoughtRepository;

    private SchoolOfThoughtService(SchoolOfThoughtRepository schoolOfThoughtRepository) {
        this.schoolOfThoughtRepository = schoolOfThoughtRepository;
    }


    public SchoolOfThought createSchoolOfThought(SchoolOfThoughtRequestDTO  dto) {


        SchoolOfThought schoolOfThought = SchoolOfThoughtMapper.toEntity(dto);

        return schoolOfThoughtRepository.save(schoolOfThought);
    }

}
