package com.floriano.philosophy_api.controllers.WorkController;

import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.WorkService.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("works")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WorkResponseDTO>> createWork(@RequestBody WorkRequestDTO dto) {

        Work work = workService.createWork(dto);

        WorkResponseDTO workResponseDTO = WorkMapper.toDTO(work);

        return new ResponseEntity<>(new ApiResponse<>(true, "Obra criada com sucesso", workResponseDTO), HttpStatus.CREATED);
    }
}
