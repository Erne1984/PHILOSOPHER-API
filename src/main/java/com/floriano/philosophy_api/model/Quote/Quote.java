package com.floriano.philosophy_api.model.Quote;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "quotes")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Philosopher philosopher;

    @ManyToOne
    private Work work;

    @ManyToMany
    @JoinTable(
            name = "quote_theme",
            joinColumns = @JoinColumn(name = "quote_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private List<Theme> themes = new ArrayList<>();

    // BIDIRECTIONALITY BETWEEN QUOTE AND THEMES
    public void addTheme(Theme theme) {
        if (!themes.contains(theme)) {
            themes.add(theme);
            theme.getQuotes().add(this);
        }
    }

    public void removeTheme(Theme theme) {
        if (themes.contains(theme)) {
            themes.remove(theme);
            theme.getQuotes().remove(this);
        }
    }

    public void clearThemes() {
        for (Theme t : List.copyOf(themes)) {
            removeTheme(t);
        }
    }
}
