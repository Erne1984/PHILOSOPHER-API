package com.floriano.philosophy_api.controllers.WorkController;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.WorkService.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/works")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @Operation(summary = "Listar todas as obras", description = "Retorna uma lista com todas as obras registradas no sistema")
    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkResponseDTO>>> getAllWorks() {
        List<WorkResponseDTO> workResponseDTOS = workService.getAllWorks();
        return ResponseEntity.ok(new ApiResponse<>(true, "Works list", workResponseDTOS));
    }

    @Operation(summary = "Buscar obra por ID", description = "Retorna os detalhes de uma obra específica pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkResponseDTO>> getWorkById(@PathVariable Long id) {
        WorkResponseDTO workResponseDTOS = workService.getWorkById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Work found", workResponseDTOS));
    }

    @Operation(summary = "Listar citações de uma obra", description = "Retorna todas as citações relacionadas a uma obra específica")
    @GetMapping("/{id}/quotes")
    public ResponseEntity<ApiResponse<List<QuoteResponseDTO>>> getQuotesByWork(@PathVariable Long id) {
        List<QuoteResponseDTO> quoteResponseDTOList = workService.getQuotesByWork(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Quotes of work found", quoteResponseDTOList));
    }

    @Operation(summary = "Criar obra", description = "Adiciona uma nova obra ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<WorkResponseDTO>> createWork(@RequestBody WorkRequestDTO dto) {
        Work work = workService.createWork(dto);
        WorkResponseDTO workResponseDTO = WorkMapper.toDTO(work);
        return new ResponseEntity<>(new ApiResponse<>(true, "Obra criada com sucesso", workResponseDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar obra", description = "Atualiza os dados de uma obra existente pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkResponseDTO>> updateWork(@PathVariable Long id, @RequestBody WorkRequestDTO dto) {
        Work updated = workService.updateWork(id, dto);
        WorkResponseDTO responseDTO = WorkMapper.toDTO(updated);
        return  new ResponseEntity<>(new ApiResponse<>(true, "Work updated successfully", responseDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar obra", description = "Remove uma obra do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkResponseDTO>> deleteWork(@PathVariable Long id){
        Work deleted = workService.deleteWork(id);
        WorkResponseDTO responseDTO = WorkMapper.toDTO(deleted);
        return  new ResponseEntity<>(new ApiResponse<>(true, "Work with id " + id + " deleted successfully", responseDTO), HttpStatus.OK);
    }
}