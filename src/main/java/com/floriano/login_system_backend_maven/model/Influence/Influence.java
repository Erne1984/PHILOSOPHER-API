package com.floriano.login_system_backend_maven.model.Influence;

import com.floriano.login_system_backend_maven.model.Philosopher.Philosopher;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "influences")
public class Influence {

    @Id
    private UUID id;

    @ManyToOne
    private Philosopher influencer;

    @ManyToOne
    private Philosopher influenced;

    private int strength;
}
