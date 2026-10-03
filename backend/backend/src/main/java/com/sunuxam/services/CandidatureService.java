package com.sunuxam.services;
import com.sunuxam.entities.Candidature;
import com.sunuxam.entities.Concours;
import com.sunuxam.entities.User;
import com.sunuxam.enums.StatutCandidature;
import com.sunuxam.repositories.CandidatureRepository;
import com.sunuxam.repositories.ConcoursRepository;
import com.sunuxam.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class CandidatureService {

        private final CandidatureRepository candidatureRepository;
        private final ConcoursRepository concoursRepository;
        private final UserRepository userRepository;

        public Candidature postuler(Long userId, Long concoursId) {
            if (candidatureRepository.existsByCandidatIdAndConcoursId(userId, concoursId)) {
                throw new RuntimeException("Vous avez déjà postulé à ce concours.");
            }

            User candidat = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
            Concours concours = concoursRepository.findById(concoursId)
                    .orElseThrow(() -> new RuntimeException("Concours non trouvé."));

            Candidature candidature = Candidature.builder()
                    .candidat(candidat)
                    .concours(concours)
                    .dateDepot(LocalDateTime.now())
                    .statut(StatutCandidature.EN_ATTENTE)
                    .build();

            return candidatureRepository.save(candidature);
        }

        public List<Candidature> getMesCandidatures(Long userId) {
            return candidatureRepository.findByCandidatId(userId);
        }

        public List<Candidature> getCandidaturesByConcours(Long concoursId) {
            return candidatureRepository.findByConcoursId(concoursId);
        }

        public Candidature changerStatut(Long candidatureId, StatutCandidature statut) {
            Candidature candidature = candidatureRepository.findById(candidatureId)
                    .orElseThrow(() -> new RuntimeException("Candidature non trouvée."));
            candidature.setStatut(statut);
            return candidatureRepository.save(candidature);
        }

        private final FileStorageService fileStorageService;

        public Candidature uploaderDocument(Long candidatureId, MultipartFile file) {
            Candidature candidature = candidatureRepository.findById(candidatureId)
                    .orElseThrow(() -> new RuntimeException("Candidature non trouvée."));

            String fileName = fileStorageService.storeFile(file);


            candidature.setDocumentUrl(fileName);

            return candidatureRepository.save(candidature);
        }

}
