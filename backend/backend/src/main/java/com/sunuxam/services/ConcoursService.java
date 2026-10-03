package com.sunuxam.services;
import com.sunuxam.entities.Concours;
import com.sunuxam.entities.Epreuve;
import com.sunuxam.repositories.ConcoursRepository;
import com.sunuxam.repositories.EpreuveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class ConcoursService {

        private final ConcoursRepository concoursRepository;
        private final EpreuveRepository epreuveRepository;

        public List<Concours> getConcoursPublics() {
            return concoursRepository.findByEstPublieTrue();
        }

        public List<Concours> getAllConcours() {
            return concoursRepository.findAll();
        }

        public Concours getConcoursById(Long id) {
            return concoursRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Concours non trouvé avec l'id : " + id));
        }

        public Concours createConcours(Concours concours) {
            return concoursRepository.save(concours);
        }

        public Concours updateConcours(Long id, Concours updated) {
            Concours concours = getConcoursById(id);
            concours.setTitre(updated.getTitre());
            concours.setDescription(updated.getDescription());
            concours.setDateLimite(updated.getDateLimite());
            concours.setDateDeliberation(updated.getDateDeliberation());
            concours.setEstPublie(updated.getEstPublie());
            return concoursRepository.save(concours);
        }

        public Epreuve addEpreuveToConcours(Long concoursId, Epreuve epreuve) {
            Concours concours = getConcoursById(concoursId);
            epreuve.setConcours(concours);
            return epreuveRepository.save(epreuve);
        }

        public List<Epreuve> getEpreuvesByConcours(Long concoursId) {
            return epreuveRepository.findByConcoursId(concoursId);
        }

}
