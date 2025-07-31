package com.floriano.philosophy_api.dto.InfluenceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfluenceResponseDTO {

    private Long id;

    private String influencer;

    private String influenced;

    private String strength;
}
