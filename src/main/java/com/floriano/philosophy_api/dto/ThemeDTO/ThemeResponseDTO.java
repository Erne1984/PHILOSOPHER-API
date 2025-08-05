package com.floriano.philosophy_api.dto.ThemeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThemeResponseDTO {

    private Long id;
    private String name;
    private String description;
    private List<String> philosophers;
    private List<String> works;
    private List<String> quotes;
}
