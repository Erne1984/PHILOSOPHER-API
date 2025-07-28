package com.floriano.philosophy_api.dto.PhilosopherDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhilosopherResponseDTO {

    private Long id;
    private String name;
    private int birthYear;
    private int deathYear;
    private String bio;
    private String country;


}
