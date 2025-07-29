package com.floriano.philosophy_api.dto.WorkDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkResponseDTO {
    private Long id;
    private String title;
    private int year;
    private String summary;
    private String philosopherName;
    private String countryName;
    private List<String> schoolsOfThought;
    private List<String> themes;
}
