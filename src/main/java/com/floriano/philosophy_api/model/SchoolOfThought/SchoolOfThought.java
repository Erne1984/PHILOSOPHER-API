package com.floriano.philosophy_api.model.SchoolOfThought;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "schools_of_thought")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SchoolOfThought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int originCentury;

    @ManyToMany(mappedBy = "schoolOfThoughts")
    private List<Philosopher> philosophers;

    @ManyToMany(mappedBy = "schoolOfThoughts")
    private List<Work> works;
}
