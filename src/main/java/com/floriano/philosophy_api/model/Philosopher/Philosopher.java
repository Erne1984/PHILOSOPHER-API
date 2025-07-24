package com.floriano.philosophy_api.model.Philosopher;

import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "philosophers")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"quotes", "works", "influenced", "influencedBy"})
public class Philosopher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int birthYear;
    private int deathYear;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "philosopher")
    private List<Quote> quotes;

    @OneToMany(mappedBy = "philosopher")
    private List<Work> works;

    @OneToMany(mappedBy = "influencer")
    private List<Influence> influenced;

    @OneToMany(mappedBy = "influenced")
    private List<Influence> influencedBy;

    @ManyToMany
    @JoinTable(
            name = "philosopher_school_of_thought",
            joinColumns = @JoinColumn(name = "philosopher_id"),
            inverseJoinColumns = @JoinColumn(name = "school_of_thought_id")
    )
    private List<SchoolOfThought> schoolOfThoughts;

    @ManyToMany
    @JoinTable(
            name = "philosopher_theme",
            joinColumns = @JoinColumn(name = "philosopher_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private List<Theme> themes;
}
