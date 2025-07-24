package com.floriano.philosophy_api.model.Work;

import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Theme.Theme;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "works")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int year;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ManyToOne
    private Philosopher philosopher;

    @OneToMany(mappedBy = "work")
    private List<Quote> quotes;

    @ManyToOne
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "work_schoolsOfThought",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "school_of_thought_id")
    )
    private List<SchoolOfThought> schoolOfThoughts;

    @ManyToMany
    @JoinTable(
            name = "work_theme",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private List<Theme> themes;
}
