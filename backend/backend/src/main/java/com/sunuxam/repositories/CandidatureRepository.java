package com.sunuxam.repositories;

import com.sunuxam.entities.Candidature;
import com.sunuxam.enums.StatutCandidature;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

    public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
        List<Candidature> findByCandidatId(Long userId);
        List<Candidature> findByConcoursId(Long concoursId);
        boolean existsByCandidatIdAndConcoursId(Long userId, Long concoursId);
        long countByStatut(StatutCandidature statut);

    }

