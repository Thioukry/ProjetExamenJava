package com.sunuxam.controllers;

import com.sunuxam.entities.Examen;
import com.sunuxam.repositories.ExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examens")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ExamenController {

    private final ExamenRepository examenRepository;

    @GetMapping
    public ResponseEntity<List<Examen>> getAllExamens() {
        return ResponseEntity.ok(examenRepository.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Examen> createExamen(@RequestBody Examen examen) {
        Examen savedExamen = examenRepository.save(examen);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExamen);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        examenRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}