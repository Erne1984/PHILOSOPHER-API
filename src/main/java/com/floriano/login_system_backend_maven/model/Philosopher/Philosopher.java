package com.floriano.login_system_backend_maven.model.Philosopher;

import com.floriano.login_system_backend_maven.model.Country.Country;
import com.floriano.login_system_backend_maven.model.Influence.Influence;
import com.floriano.login_system_backend_maven.model.Quote.Quote;
import com.floriano.login_system_backend_maven.model.SchoolOfThought.SchoolOfThought;
import com.floriano.login_system_backend_maven.model.Theme.Theme;
import com.floriano.login_system_backend_maven.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "philosophers")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Philosopher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;
    private int birthYear;
    private int deathYear;

    @ManyToOne
    private Country country;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ManyToMany
    private List<SchoolOfThought> schoolOfThoughts;

    @OneToMany(mappedBy = "philosopher")
    private List<Work> works;

    @OneToMany(mappedBy = "influencer")
    private List<Influence> influenced;

    @OneToMany(mappedBy = "influenced")
    private List<Influence> influencedBy;

    @ManyToMany
    private List<Theme> themes;

    @OneToMany(mappedBy = "philosopher")
    private List<Quote> quotes;
}
