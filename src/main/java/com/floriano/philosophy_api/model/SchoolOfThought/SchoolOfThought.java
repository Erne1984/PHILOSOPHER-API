package com.floriano.philosophy_api.model.SchoolOfThought;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Work.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private List<Philosopher> philosophers = new ArrayList<>();

    @ManyToMany(mappedBy = "schoolOfThoughts")
    private List<Work> works = new ArrayList<>();

    // BIDIRECTIONALITY BETWEEN SCHOOLOFTHOUGHT AND PHILOSOPHER

    public void addPhilosopher(Philosopher philosopher) {
        if(!this.philosophers.contains(philosopher)) {
            this.getPhilosophers().add(philosopher);
        }
        if(!philosopher.getSchoolOfThoughts().contains(this)) {
            philosopher.getSchoolOfThoughts().add(this);
        }
    }

    public void removePhilosopher(Philosopher philosopher) {
        this.philosophers.remove(philosopher);
        philosopher.getSchoolOfThoughts().remove(this);
    }

    public void clearPhilosophers() {
        for (Philosopher p : List.copyOf(philosophers)) {
            removePhilosopher(p);
        }
    }

    // BIDIRECTIONALITY BETWEEN SCHOOLOFTHOUGHT AND WORK

    public void addWork(Work work) {
        if(!this.works.contains(work)) {
            this.getWorks().add(work);
        }
        if(!work.getSchoolOfThoughts().contains(this)) {
            work.getSchoolOfThoughts().add(this);
        }
    }

    public void removeWork(Work work) {
        this.works.remove(work);
        work.getSchoolOfThoughts().remove(this);
    }

    public void clearWorks() {
        for (Work w : List.copyOf(works)) {
            removeWork(w);
        }
    }
}
