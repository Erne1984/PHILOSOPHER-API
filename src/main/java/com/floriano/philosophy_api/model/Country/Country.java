package com.floriano.philosophy_api.model.Country;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String isoCode;
    private int startYear;
    private int endYear;
    private String region;

    @OneToMany(mappedBy = "country")
    private List<Philosopher> philosophers;

    @OneToMany(mappedBy = "country")
    private List<Work> works;
}
