package com.sunuxam.controllers;
import com.sunuxam.entities.Concours;
import com.sunuxam.entities.Epreuve;
import com.sunuxam.services.ConcoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/concours")
    @RequiredArgsConstructor
    public class ConcoursController {

        private final ConcoursService concoursService;

        @GetMapping("/public")
        public ResponseEntity<List<Concours>> getPublicConcours() {
            return ResponseEntity.ok(concoursService.getConcoursPublics());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Concours> getConcoursById(@PathVariable Long id) {
            return ResponseEntity.ok(concoursService.getConcoursById(id));
        }

        @PostMapping("/admin")
        public ResponseEntity<Concours> createConcours(@RequestBody Concours concours) {
            return ResponseEntity.ok(concoursService.createConcours(concours));
        }

        @PostMapping("/admin/{concoursId}/epreuves")
        public ResponseEntity<Epreuve> addEpreuve(@PathVariable Long concoursId, @RequestBody Epreuve epreuve) {
            return ResponseEntity.ok(concoursService.addEpreuveToConcours(concoursId, epreuve));
        }

        @GetMapping("/{concoursId}/epreuves")
        public ResponseEntity<List<Epreuve>> getEpreuves(@PathVariable Long concoursId) {
            return ResponseEntity.ok(concoursService.getEpreuvesByConcours(concoursId));
        }

}
