package com.floriano.philosophy_api.model.Theme;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private List<Philosopher> philosophers = new ArrayList<>();

    @ManyToMany(mappedBy = "themes")
    private List<Work> works = new ArrayList<>();

    @ManyToMany(mappedBy = "themes")
    private List<Quote> quotes = new ArrayList<>();

    // BIDIRECTIONALITY BETWEEN THEME AND PHILOSOPHER
    public void addPhilosopher(Philosopher philosopher) {
        if (!philosophers.contains(philosopher)) {
            philosophers.add(philosopher);
            philosopher.getThemes().add(this);
        }
    }

    public void removePhilosopher(Philosopher philosopher) {
        if (philosophers.contains(philosopher)) {
            philosophers.remove(philosopher);
            philosopher.getThemes().remove(this);
        }
    }

    public void clearPhilosophers() {
        for (Philosopher p : List.copyOf(philosophers)) {
            removePhilosopher(p);
        }
    }

    // BIDIRECTIONALITY BETWEEN THEME AND WORKS
    public void addWork(Work work) {
        if (!works.contains(work)) {
            works.add(work);
            work.getThemes().add(this);
        }
    }

    public void removeWork(Work work) {
        if (works.contains(work)) {
            works.remove(work);
            work.getThemes().remove(this);
        }
    }

    public void clearWorks() {
        for (Work w : List.copyOf(works)) {
            removeWork(w);
        }
    }

    // BIDIRECTIONALITY BETWEEN THEME AND QUOTES
    public void addQuote(Quote quote) {
        if (!quotes.contains(quote)) {
            quotes.add(quote);
            quote.getThemes().add(this);
        }
    }

    public void removeQuote(Quote quote) {
        if (quotes.contains(quote)) {
            quotes.remove(quote);
            quote.getThemes().remove(this);
        }
    }

    public void clearQuotes() {
        for (Quote q : List.copyOf(quotes)) {
            removeQuote(q);
        }
    }
}
