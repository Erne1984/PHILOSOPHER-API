package com.floriano.philosophy_api.controllers.WorkController;

import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.WorkService.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("works")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkResponseDTO>>> getAllWorks() {
        List<Work> works = workService.getAllWorks();

        List<WorkResponseDTO> dtoList = works.stream()
                .map(WorkMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "Works list", dtoList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WorkResponseDTO>> createWork(@RequestBody WorkRequestDTO dto) {

        Work work = workService.createWork(dto);

        WorkResponseDTO workResponseDTO = WorkMapper.toDTO(work);

        return new ResponseEntity<>(new ApiResponse<>(true, "Obra criada com sucesso", workResponseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkResponseDTO>> updateWork(@PathVariable Long id, @RequestBody WorkRequestDTO dto) {

        Work updated = workService.updateWork(id, dto);
        WorkResponseDTO responseDTO = WorkMapper.toDTO(updated);

        return  new ResponseEntity<>(new ApiResponse<>(true, "Work updated successfully!!", responseDTO), HttpStatus.OK);
    }
}
