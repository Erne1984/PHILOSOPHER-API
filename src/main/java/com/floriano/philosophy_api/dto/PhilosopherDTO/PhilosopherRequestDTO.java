package com.floriano.philosophy_api.dto.PhilosopherDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhilosopherRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Min(value = -1000, message = "Ano de nascimento inválido")
    @Max(value = 2025, message = "Ano de nascimento inválido")
    private int birthYear;

    @Max(value = 2025, message = "Ano de morte inválido")
    private Integer deathYear;

    @Size(max = 5000, message = "Biografia muito longa")
    private String bio;

    @Positive(message = "ID do país deve ser positivo")
    private Long countryId;

    private List<Long> quotesId;

    private List<Long> schoolOfThoughtsIds;
    private List<Long> themesIds;
}
