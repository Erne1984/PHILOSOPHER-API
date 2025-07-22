package com.floriano.login_system_backend_maven.model.Influence;

import com.floriano.login_system_backend_maven.model.Philosopher.Philosopher;
import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "influences")
@ToString
public class Influence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Philosopher influencer;

    @ManyToOne
    private Philosopher influenced;

    @Enumerated(EnumType.STRING)
    private InfluenceStrength strength;
}
