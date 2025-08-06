package com.floriano.philosophy_api.dto.WorkDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkRequestDTO {

    @NotBlank(message = "Titúlo é obrigatório")
    private String title;

    private Integer year;

    @Size(max = 5000, message = "Sumário muito longo")
    private String summary;

    private Long philosopherId;
    private Long countryId;
    private List<Long> schoolOfThoughtIds;
    private List<Long> themeIds;

}
