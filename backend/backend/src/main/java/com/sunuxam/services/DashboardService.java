package com.sunuxam.services;
import com.sunuxam.dtos.DashboardDTO;
import com.sunuxam.enums.StatutCandidature;
import com.sunuxam.repositories.CandidatureRepository;
import com.sunuxam.repositories.ConcoursRepository;
import com.sunuxam.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class DashboardService {

        private final UserRepository userRepository;
        private final ConcoursRepository concoursRepository;
        private final CandidatureRepository candidatureRepository;

        public DashboardDTO getDashboardStats() {
            return DashboardDTO.builder()
                    .totalCandidats(userRepository.count())
                    .totalConcours(concoursRepository.count())
                    .totalCandidatures(candidatureRepository.count())
                    .candidaturesEnAttente(candidatureRepository.countByStatut(StatutCandidature.EN_ATTENTE))
                    .candidaturesAcceptees(candidatureRepository.countByStatut(StatutCandidature.ACCEPTEE))
                    .candidaturesRejetees(candidatureRepository.countByStatut(StatutCandidature.REJETEE))
                    .build();
        }

}
