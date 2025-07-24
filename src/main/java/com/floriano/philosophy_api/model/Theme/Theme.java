package com.floriano.philosophy_api.model.Theme;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "themes")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "themes")
    private List<Philosopher> philosophers;

    @ManyToMany(mappedBy = "themes")
    private List<Work> works;

    @ManyToMany(mappedBy = "themes")
    private List<Quote> quotes;
}
