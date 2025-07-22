package com.floriano.login_system_backend_maven.controllers.PhilosopherController;

import com.floriano.login_system_backend_maven.model.Philosopher.Philosopher;
import com.floriano.login_system_backend_maven.services.PhilosopherService.PhilosopherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("philosophers")
public class PhilosopherController {

    private final PhilosopherService philosopherService;

    public PhilosopherController(PhilosopherService philosopherService) {
        this.philosopherService = philosopherService;
    }

    @GetMapping
    public List<Philosopher> getPhilosophers() {
        return philosopherService.getAllPhilosopher();
    }

    @PostMapping
    public Philosopher createPhilosopher(@RequestBody Philosopher philosopher) {
        return philosopherService.createPhilosopher(philosopher);
    }
}
