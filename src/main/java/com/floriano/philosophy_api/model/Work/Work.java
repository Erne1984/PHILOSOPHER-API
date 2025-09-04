package com.floriano.philosophy_api.model.Work;

import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Theme.Theme;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private String language;

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
            name = "work_schools_of_thought",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "school_of_thought_id")
    )
    private List<SchoolOfThought> schoolOfThoughts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "work_theme",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private List<Theme> themes = new ArrayList<>();

    // BIDIRECTIONALITY BETWEEN WORK AND SCHOOLOFTHOUGHT

    public void addSchoolOfThought(SchoolOfThought school) {
        if (!this.schoolOfThoughts.contains(school)) {
            this.schoolOfThoughts.add(school);
        }
        if (!school.getWorks().contains(this)) {
            school.getWorks().add(this);
        }
    }

    public void removeSchoolOfThought(SchoolOfThought school) {
        this.schoolOfThoughts.remove(school);
        school.getWorks().remove(this);
    }

    public void clearSchoolOfThoughts() {
        for (SchoolOfThought s : List.copyOf(schoolOfThoughts)) {
            removeSchoolOfThought(s);
        }
    }

    // BIDIRECTIONALITY BETWEEN WORK AND THEME

    public void addTheme(Theme theme) {
        if (!this.themes.contains(theme)) {
            this.themes.add(theme);
        }
        if (!theme.getWorks().contains(this)) {
            theme.getWorks().add(this);
        }
    }

    public void removeTheme(Theme theme) {
        this.themes.remove(theme);
        theme.getWorks().remove(this);
    }

    public void clearThemes() {
        for (Theme t : List.copyOf(themes)) {
            removeTheme(t);
        }
    }
}