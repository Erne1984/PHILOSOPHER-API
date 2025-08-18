package com.floriano.philosophy_api.controllers.QuoteController;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.QuoteService.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Operation(summary = "Listar todas as citações", description = "Retorna uma lista com todas as citações registradas no sistema")
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuoteResponseDTO>>> getQuotes() {
        List<QuoteResponseDTO> dtoList = quoteService.getAllQuotes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Quote List", dtoList));
    }

    @Operation(summary = "Buscar citação por ID", description = "Retorna os detalhes de uma citação específica pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> getQuoteById(@PathVariable Long id) {
        QuoteResponseDTO quoteResponseDTO = quoteService.getQuoteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Quote found", quoteResponseDTO));
    }

    @Operation(summary = "Criar citação", description = "Adiciona uma nova citação ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> createQuote(@RequestBody QuoteRequestDTO dto) {
        Quote created = quoteService.createQuote(dto);
        QuoteResponseDTO responseDTO = QuoteMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Citação criada com sucesso", responseDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar citação", description = "Atualiza os dados de uma citação existente pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> updateQuote(@PathVariable Long id, @RequestBody QuoteRequestDTO dto) {
        Quote updated = quoteService.updateQuote(id, dto);
        QuoteResponseDTO response = QuoteMapper.toDTO(updated);
        return  new ResponseEntity<>(new ApiResponse<>(true, "Citação atualizada com sucesso!", response), HttpStatus.OK);
    }

    @Operation(summary = "Deletar citação", description = "Remove uma citação do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuote(@PathVariable Long id){
        quoteService.deleteQuote(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Quote with id " + id + " successfully deleted!", null), HttpStatus.OK);
    }
}
