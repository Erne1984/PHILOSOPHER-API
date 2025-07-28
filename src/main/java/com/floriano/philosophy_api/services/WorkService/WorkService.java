package com.floriano.philosophy_api.services.WorkService;

import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkService {

    private final WorkRepository workRepository;

    public WorkService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }


    public
}
