package com.sunuxam.controllers;
import com.sunuxam.dtos.ResultatCandidatDTO;
import com.sunuxam.entities.Note;
import com.sunuxam.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/notes")
    @RequiredArgsConstructor
    public class NoteController {

        private final NoteService noteService;

        @PostMapping("/admin/saisir")
        public ResponseEntity<Note> saisirNote(
                @RequestParam Long candidatureId,
                @RequestParam Long epreuveId,
                @RequestParam Double valeur
        ) {
            return ResponseEntity.ok(noteService.saisirNote(candidatureId, epreuveId, valeur));
        }

        @GetMapping("/candidature/{candidatureId}")
        public ResponseEntity<List<Note>> getNotesParCandidature(@PathVariable Long candidatureId) {
            return ResponseEntity.ok(noteService.getNotesParCandidature(candidatureId));
        }

        @GetMapping("/candidature/{candidatureId}/moyenne")
        public ResponseEntity<Double> getMoyenneCandidat(@PathVariable Long candidatureId) {
            return ResponseEntity.ok(noteService.calculerMoyenneCandidature(candidatureId));
        }

        @GetMapping("/admin/concours/{concoursId}/resultats")
        public ResponseEntity<List<ResultatCandidatDTO>> getResultatsConcours(@PathVariable Long concoursId) {
            return ResponseEntity.ok(noteService.getRésultatsParConcours(concoursId));
        }

}
