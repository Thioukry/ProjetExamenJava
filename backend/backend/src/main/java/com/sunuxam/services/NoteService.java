package com.sunuxam.services;
import com.sunuxam.entities.Candidature;
import com.sunuxam.entities.Epreuve;
import com.sunuxam.entities.Note;
import com.sunuxam.repositories.CandidatureRepository;
import com.sunuxam.repositories.EpreuveRepository;
import com.sunuxam.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sunuxam.dtos.ResultatCandidatDTO;
import com.sunuxam.repositories.ConcoursRepository;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class NoteService {

        private final NoteRepository noteRepository;
        private final CandidatureRepository candidatureRepository;
        private final EpreuveRepository epreuveRepository;

        public Note saisirNote(Long candidatureId, Long epreuveId, Double valeurNote) {
            Candidature candidature = candidatureRepository.findById(candidatureId)
                    .orElseThrow(() -> new RuntimeException("Candidature non trouvée."));
            Epreuve epreuve = epreuveRepository.findById(epreuveId)
                    .orElseThrow(() -> new RuntimeException("Épreuve non trouvée."));

            Note note = noteRepository.findByCandidatureIdAndEpreuveId(candidatureId, epreuveId)
                    .orElse(Note.builder()
                            .candidature(candidature)
                            .epreuve(epreuve)
                            .build());

            note.setValeur(valeurNote);
            return noteRepository.save(note);
        }

        public List<Note> getNotesParCandidature(Long candidatureId) {
            return noteRepository.findByCandidatureId(candidatureId);
        }



        private final ConcoursRepository concoursRepository;

        public Double calculerMoyenneCandidature(Long candidatureId) {
            List<Note> notes = noteRepository.findByCandidatureId(candidatureId);
            if (notes.isEmpty()) {
                return 0.0;
            }

            double totalPondere = 0.0;
            double totalCoefficients = 0.0;

            for (Note note : notes) {
                double coef = note.getEpreuve().getCoefficient();
                totalPondere += note.getValeur() * coef;
                totalCoefficients += coef;
            }

            if (totalCoefficients == 0) return 0.0;


            return Math.round((totalPondere / totalCoefficients) * 100.0) / 100.0;
        }

        public List<ResultatCandidatDTO> getRésultatsParConcours(Long concoursId) {
            List<Candidature> candidatures = candidatureRepository.findByConcoursId(concoursId);
            List<ResultatCandidatDTO> resultats = new ArrayList<>();

            for (Candidature c : candidatures) {
                Double moyenne = calculerMoyenneCandidature(c.getId());

                ResultatCandidatDTO dto = ResultatCandidatDTO.builder()
                        .candidatureId(c.getId())
                        .nomCandidat(c.getCandidat().getNom())
                        .prenomCandidat(c.getCandidat().getPrenom())
                        .emailCandidat(c.getCandidat().getEmail())
                        .moyenne(moyenne)
                        .statut(c.getStatut())
                        .build();

                resultats.add(dto);
            }


            resultats.sort(Comparator.comparing(ResultatCandidatDTO::getMoyenne).reversed());
            return resultats;
        }

}
