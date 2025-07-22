package com.floriano.login_system_backend_maven.model.Country;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.floriano.login_system_backend_maven.model.Philosopher.Philosopher;
import com.floriano.login_system_backend_maven.model.Work.Work;
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
