package com.floriano.login_system_backend_maven.model.Quote;

import com.floriano.login_system_backend_maven.model.Philosopher.Philosopher;
import com.floriano.login_system_backend_maven.model.Theme.Theme;
import com.floriano.login_system_backend_maven.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

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
    private List<Theme> themes;
}
