package com.floriano.philosophy_api.controllers.PhilosopherController;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.PhilosopherService.PhilosopherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<Philosopher>>> getPhilosophers() {
        List<Philosopher> list = philosopherService.getAllPhilosopher();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de filósofos", list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Philosopher>> createPhilosopher(@RequestBody Philosopher philosopher) {
        Philosopher created = philosopherService.createPhilosopher(philosopher);
        return new ResponseEntity<>(new ApiResponse<>(true, "Filósofo criado com sucesso", created), HttpStatus.CREATED);
    }
}
